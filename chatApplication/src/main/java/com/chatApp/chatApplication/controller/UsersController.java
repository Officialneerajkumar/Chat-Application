package com.chatApp.chatApplication.controller;

import com.chatApp.chatApplication.dao.StatusRepository;
import com.chatApp.chatApplication.dao.UserRepository;
import com.chatApp.chatApplication.model.Status;
import com.chatApp.chatApplication.model.Users;
import com.chatApp.chatApplication.service.UsersService;
import com.chatApp.chatApplication.util.CommonUtils;
import jakarta.annotation.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/user")
public class UsersController {
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private UsersService usersService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/create-user")
    public ResponseEntity<String> createUser(@RequestBody String userData){
        JSONObject isValid = validateUserRequest(userData);

        if(isValid.isEmpty()){
            Users user = setUser(userData);
            int userId = usersService.saveUser(user);
            return new ResponseEntity<>("saved "+userId,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(isValid.toString(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-users")
    public ResponseEntity<String> getUsers(@Nullable @RequestParam String userId){
        JSONArray userArray = usersService.getUsers(userId);
        return new ResponseEntity<>(userArray.toString(),HttpStatus.OK);
    }


    @DeleteMapping("/delete-user/userId/{userId}")
    public ResponseEntity<String> deleteUserByUserId(@PathVariable int userId){
        usersService.deleteUserByUserId(userId);
        return new ResponseEntity<>("user deleted! ",HttpStatus.OK);
    }

    @PostMapping("/log-in")
    public ResponseEntity<String> login(@RequestBody String userData){
        JSONObject requestJson = new JSONObject(userData);

        JSONObject isValidLogin = isValidLogin(requestJson);
        if(isValidLogin.isEmpty()){
            String userName = requestJson.getString("userName");
            JSONObject user = usersService.login(userName);
            return new ResponseEntity<>(user.toString(),HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>(isValidLogin.toString(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update-user/{userId}")  // incomplete pending some validation that should be remove extra validation is there .
    public ResponseEntity<String> updateUser(@PathVariable String userId,@RequestBody String requestData){
        JSONObject isValidRequest = validateUserRequest(requestData);

        if(isValidRequest.isEmpty()){
            Users user = setUser(requestData);
            JSONObject responseObj = usersService.updateUser(user,userId);
            if(!responseObj.isEmpty()){
                return new ResponseEntity<>(responseObj.toString(),HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>(isValidRequest.toString(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("user updated ",HttpStatus.OK);
    }

    private JSONObject isValidLogin(JSONObject requestJson) {
        JSONObject errorList = new JSONObject();
        List<Users> userList = null;

        if(requestJson.has("userName")){
            String userName = requestJson.getString("userName");
            userList = userRepository.findByUserName(userName);
            if(userList.isEmpty()){
                errorList.put("error message ","Please enter valid userName ");
                return errorList;
            }
        }else{
            errorList.put("userName ","missing parameter");
        }

        if(requestJson.has("password")){
            Users user = userList.get(0);
            String password = requestJson.getString("password");
            if(!user.getPassword().equals(password)){
                errorList.put("error message ","Please enter valid password");
            }
        }else{
            errorList.put("password ","missing parameter");
        }

        return errorList;
    }


    private Users setUser(String userData) {
        Users user = new Users();
        JSONObject userJson = new JSONObject(userData);

// mandatory parameter
        user.setUserName(userJson.getString("userName"));
        user.setPassword(userJson.getString("password"));
        user.setFirstName(userJson.getString("firstName"));
        user.setEmail(userJson.getString("email"));
        user.setPhoneNumber(userJson.getString("phoneNumber"));

// non mandatory parameter
        if(userJson.has("age")){
            user.setAge(userJson.getInt("age"));
        }
        if(userJson.has("lastName")){
            user.setLastName(userJson.getString("lastName"));
        }
        if(userJson.has("gender")){
            user.setGender(userJson.getString("gender"));
        }
        Timestamp createdTime = new Timestamp(System.currentTimeMillis());
        user.setCreateDate(createdTime);

        Status status = statusRepository.findById(1).get();
        user.setStatusId(status);

        return user;
    }

    private JSONObject validateUserRequest(String userData) {
        JSONObject userJson = new JSONObject(userData);
        JSONObject errorList = new JSONObject();

        if(userJson.has("userName")){
            String userName = userJson.getString("userName");
            List<Users> usersList = userRepository.findByUserName(userName);
            if(usersList.size()>0){
                errorList.put("userName","This userName is already exists");
                // if userName is not uniqe than it will return providde uniqe username we have no need to check furder
                return errorList;
            }
        }else{
            errorList.put("userName","Missing parameter");
        }

        if(userJson.has("password")){
            String password = userJson.getString("password");
            if(!CommonUtils.isValidPassword(password)){
                errorList.put("password","Please enter valid password");
            }
        }else{
            errorList.put("password","Missing parameter");
        }

        if(userJson.has("firstName")){
            String firstName = userJson.getString("firstName");
        }else{
            errorList.put("firstName","Missing parameter");
        }

        if(userJson.has("email")){
            String email = userJson.getString("email");
            if(!CommonUtils.isValidEmail(email)){
                errorList.put("email","Please enter valid email");
            }
        }else{
            errorList.put("email","Missing parameter");
        }

        if(userJson.has("phoneNumber")){
            String phoneNumber = userJson.getString("phoneNumber");
            if(!CommonUtils.isValidPhoneNumber(phoneNumber)){
                errorList.put("phoneNumber","Please enter valid phoneNumber");
            }
        }else{
            errorList.put("phoneNumber","Missing parameter");
        }

        if(userJson.has("age")){ // these are not valid
            String age = userJson.getString("age");
        }
        if(userJson.has("lastName")){
            String lastName = userJson.getString("lastName");
        }
        return errorList;
    }
}
