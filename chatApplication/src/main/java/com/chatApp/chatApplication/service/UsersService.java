package com.chatApp.chatApplication.service;

import com.chatApp.chatApplication.dao.UserRepository;
import com.chatApp.chatApplication.model.Users;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;


@Service
public class UsersService {


    @Autowired
    private UserRepository userRepository;
    public int saveUser(Users user) {
        Users userObj = userRepository.save(user);
        return userObj.getUserId();
    }

    public JSONArray getUsers(String userId) {
        JSONArray response = new JSONArray();
        if(userId != null){
            List<Users> userList = userRepository.getUserByUserId(Integer.valueOf(userId));
            for(Users user:userList){
                JSONObject userObj = createResponse(user);
                response.put(userObj);
            }
        }else{
            List<Users> userList = userRepository.getAllUsers();
            for(Users user:userList){
                JSONObject userObj = createResponse(user);
                response.put(userObj);
            }
        }
        return response;
    }

    private JSONObject createResponse(Users user) {
        JSONObject jsonObj = new JSONObject();

        jsonObj.put("userId",user.getUserId());
        jsonObj.put("userName",user.getUserName());
        jsonObj.put("firstName",user.getFirstName());
        jsonObj.put("lastName",user.getLastName());
        jsonObj.put("age",user.getAge());
        jsonObj.put("email",user.getEmail());
        jsonObj.put("phoneNumber",user.getPhoneNumber());
        jsonObj.put("gender",user.getGender());
        jsonObj.put("createDate",user.getCreateDate());

        return jsonObj;
    }

    public JSONObject login(String userName) {
         List<Users> users = userRepository.findByUserName(userName);
         Users user = users.get(0);
         JSONObject response = createResponse(user);
         return response;
    }

    public JSONObject updateUser(Users newUser, String userId) {
        List<Users> usersList = userRepository.getUserByUserId(Integer.valueOf(userId));
        JSONObject obj = new JSONObject();
        if(!usersList.isEmpty()){
            Users oldUser = usersList.get(0);
            newUser.setUserId(oldUser.getUserId());
            newUser.setCreateDate(oldUser.getCreateDate());
            Timestamp updatedDate = new Timestamp(System.currentTimeMillis());
            newUser.setUpdateDate(updatedDate);
            userRepository.save(newUser);
        }else{
            obj.put("error message","user does not exist");
        }
        return obj;
    }

    public void deleteUserByUserId(int userId) {
        userRepository.deleteUserByUserId(userId);
    }
}
