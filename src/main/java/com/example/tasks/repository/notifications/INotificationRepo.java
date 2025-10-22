package com.example.tasks.repository.notifications;

import com.example.tasks.repository.notifications.model.NotificationEntity;

import java.util.List;

/**
 * Общий интерфейс
 */
public interface INotificationRepo {

    List<NotificationEntity> findAllByUser(final Long userId);

    List<NotificationEntity> findPendingByUser(final Long userId);

    NotificationEntity save(final NotificationEntity entity);
}
