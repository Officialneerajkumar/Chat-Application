package com.chatApp.chatApplication.dao;

import com.chatApp.chatApplication.model.ChatHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatHistoryRepository extends JpaRepository<ChatHistory,Integer> {
}
