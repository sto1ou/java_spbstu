package com.example.tasks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class TasksApplication {

    public static void main(String[] args) {
        SpringApplication.run(TasksApplication.class, args);
    }

}
