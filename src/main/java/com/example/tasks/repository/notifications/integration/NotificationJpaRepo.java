package com.example.tasks.repository.notifications.integration;

import com.example.tasks.repository.notifications.INotificationRepo;
import com.example.tasks.repository.notifications.model.NotificationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.tasks.repository.GlobalStatus.PENDING;

@Profile("!mem")
@Repository
public class NotificationJpaRepo implements INotificationRepo {

    @Autowired
    private INotificationJpaRepo iNotificationJpaRepo;

    @Override
    public List<NotificationEntity> findAllByUser(final Long userId) {
        return iNotificationJpaRepo.findByUser(userId);
    }

    @Override
    public List<NotificationEntity> findPendingByUser(final Long userId) {
        return iNotificationJpaRepo.findByUserAndStatus(userId, PENDING.name());
    }

    @Override
    public NotificationEntity save(final NotificationEntity entity) {
        return iNotificationJpaRepo.save(entity);
    }
}
