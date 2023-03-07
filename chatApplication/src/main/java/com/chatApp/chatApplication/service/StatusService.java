package com.chatApp.chatApplication.service;

import com.chatApp.chatApplication.dao.StatusRepository;
import com.chatApp.chatApplication.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;
    public int  saveStatus(Status status) {
        Status status1 = statusRepository.save(status);
        int statusId = status1.getStatusId();
        return statusId;
    }
}
