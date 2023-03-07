package com.chatApp.chatApplication.controller;

import com.chatApp.chatApplication.model.Status;
import com.chatApp.chatApplication.service.StatusService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/status")
public class StatusController {

    @Autowired
    private StatusService statusService;
    @PostMapping("/status") // incomplete  not giving right out put
    public ResponseEntity<String> createStatus(@RequestBody Status status){
        int statusId = statusService.saveStatus(status);
        return new ResponseEntity<>("status saved "+statusId,HttpStatus.CREATED);
    }
}
