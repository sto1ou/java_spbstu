package com.example.tasks.service.notifications;

import com.example.tasks.repository.notifications.INotificationRepo;
import com.example.tasks.repository.notifications.model.NotificationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService implements INotificationService {

    @Autowired
    private INotificationRepo iNotificationRepo;

    @Override
    public List<NotificationEntity> findAll(final Long user) {
        return iNotificationRepo.findAllByUser(user);
    }

    @Override
    public List<NotificationEntity> findPending(final Long user) {
        return iNotificationRepo.findPendingByUser(user);
    }

    @Override
    public NotificationEntity save(final NotificationEntity entity) {
        return iNotificationRepo.save(entity);
    }
}
