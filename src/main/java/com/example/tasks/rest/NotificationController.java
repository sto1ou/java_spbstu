package com.example.tasks.rest;

import com.example.tasks.repository.notifications.model.NotificationEntity;
import com.example.tasks.service.notifications.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private INotificationService iNotificationService;

    @GetMapping
    public ResponseEntity<List<NotificationEntity>> getUserNotifications(@RequestParam("user") final Long user) {
        return ok(iNotificationService.findAll(user));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<NotificationEntity>> getPendingUserNotifications(@RequestParam("user") final Long user) {
        return ok(iNotificationService.findPending(user));
    }
}
