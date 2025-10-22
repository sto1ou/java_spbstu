package com.example.tasks.repository.notifications.model;

import com.example.tasks.repository.GlobalStatus;
import lombok.Data;

@Data
public class NotificationEntity {

    private Long id;
    private Long user;
    private Long created;
    private String data;
    private GlobalStatus status;
}
