package com.example.tasks.repository.notifications.integration;

import com.example.tasks.repository.GlobalStatus;
import com.example.tasks.repository.notifications.model.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface INotificationJpaRepo extends JpaRepository<NotificationEntity, Long> {

    @Override
    @Transactional
    <S extends NotificationEntity> S save(final S entity);

    @Transactional
    List<NotificationEntity> findByUser(final Long user);

    @Transactional
    List<NotificationEntity> findByUserAndStatus(final Long user, final GlobalStatus status);
}