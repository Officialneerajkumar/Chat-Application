package com.chatApp.chatApplication.service;

import com.chatApp.chatApplication.dao.ChatHistoryRepository;
import com.chatApp.chatApplication.model.ChatHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    @Autowired
    private ChatHistoryRepository chatHistoryRepository;

    public int saveMessage(ChatHistory chat) {
        ChatHistory chatObj = chatHistoryRepository.save(chat);
        return chatObj.getChatId();
    }
}
