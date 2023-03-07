package com.chatApp.chatApplication.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
@Entity // it tells to database that chatHistory is a object of database
@Data
@AllArgsConstructor
@NoArgsConstructor                    //  ### time = 3:29:30  video done till it.
@Table(name = "tbl_chat_history")
public class ChatHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private int chatId;
    @JoinColumn(name = "sender_id")
    @ManyToOne
    private Users sender;
    @JoinColumn(name = "reciever_id")
    @ManyToOne
    private Users reciever;
    @Column(name = "message")
    private String message;

//    @CreationTimestamp
//    @Column(nullable = false,updatable = false,name = "") // createDate can not be null and also can not be update
    @Column(name = "create_date")
    private Timestamp createDate;
    @Column(name = "update_date")
    private Timestamp updateDate;

    @ManyToOne
    @JoinColumn(name = "status_id")// mapping with status table by statusId
    private Status statusId;

}
