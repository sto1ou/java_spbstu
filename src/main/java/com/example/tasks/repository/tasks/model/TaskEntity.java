package com.example.tasks.repository.tasks.model;

import com.example.tasks.repository.GlobalStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskEntity {

    private Long id;
    private String name;
    private Long target;
    private Long created;
    private Long user;
    private GlobalStatus status;
}
