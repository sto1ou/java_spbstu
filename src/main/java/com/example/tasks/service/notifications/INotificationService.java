package com.example.tasks.service.notifications;

import com.example.tasks.repository.notifications.model.NotificationEntity;

import java.util.List;

public interface INotificationService {

    List<NotificationEntity> findAll(final Long user);

    List<NotificationEntity> findPending(final Long user);

    NotificationEntity save(final NotificationEntity entity);
}
