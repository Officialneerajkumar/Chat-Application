package com.chatApp.chatApplication.controller;

import com.chatApp.chatApplication.dao.StatusRepository;
import com.chatApp.chatApplication.dao.UserRepository;
import com.chatApp.chatApplication.model.ChatHistory;
import com.chatApp.chatApplication.model.Status;
import com.chatApp.chatApplication.model.Users;
import com.chatApp.chatApplication.service.ChatService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
@RequestMapping(value = "/api/v1/chat-history")
public class ChatHistoryController {

    @Autowired
    private ChatService chatService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StatusRepository statusRepository;

    @PostMapping("/send-message")
    public ResponseEntity<String> sendMessage(@RequestBody String requestData){
        JSONObject requestJson = new JSONObject(requestData);
        JSONObject errorList = isValidateMessage(requestJson);

        if(errorList.isEmpty()){
            ChatHistory chat = setChatHistory(requestJson);
            int chatId  = chatService.saveMessage(chat);
            return new ResponseEntity<>("message sent "+chatId,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(errorList.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    private ChatHistory setChatHistory(JSONObject requestJson) {
        ChatHistory chat = new ChatHistory();

        int senderId = requestJson.getInt("sender");
        int recieverId = requestJson.getInt("reciever");
        String message = requestJson.getString("message");

        Users senderObj = userRepository.findById(senderId).get();
        Users recieverObj = userRepository.findById(recieverId).get();
        Status statusObj = statusRepository.findById(1).get();

        chat.setSender(senderObj);
        chat.setReciever(recieverObj);
        chat.setStatusId(statusObj);
        chat.setMessage(message);

        Timestamp createTime = new Timestamp(System.currentTimeMillis());
        chat.setCreateDate(createTime);

        return chat;
    }

    private JSONObject isValidateMessage(JSONObject requestJson) {
        JSONObject errorList = new JSONObject();

        if(!requestJson.has("sender")){
            errorList.put("sender","missing parameter");
        }
        if(!requestJson.has("reciever")){
            errorList.put("reciever","missing parameter");
        }
        if(requestJson.has("message")){
            String message = requestJson.getString("message");
            if(message.isEmpty() && message.isBlank()){
                errorList.put("message","message body cant't be empty");
            }
        }else{
            errorList.put("message","missing parameter");
        }
        return errorList;
    }
}
