package com.example.tasks.repository.notifications.integration;

import com.example.tasks.repository.notifications.model.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface INotificationJpaRepo extends JpaRepository<NotificationEntity, Long> {

    @Override
    @Transactional
    <S extends NotificationEntity> S save(final S entity);

    @Transactional
    List<NotificationEntity> findByUser(final Long user);

    @Transactional
    @Query(
            value = "select * from notifications where \"user\" = :userId and status = (:status)::global_status",
            nativeQuery = true
    )
    List<NotificationEntity> findByUserAndStatus(@Param("userId") final Long user, @Param("status") final String status);
}