package com.example.tasks.repository.notifications;

import com.example.tasks.repository.notifications.model.NotificationEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.example.tasks.repository.GlobalStatus.PENDING;

@Profile("mem")
@Repository
public class NotificationRepo implements INotificationRepo {

    private static final Map<Long, Set<NotificationEntity>> USER_NOTIFICATIONS = new HashMap<>();

    @Override
    public List<NotificationEntity> findAllByUser(final Long user) {
        return USER_NOTIFICATIONS.getOrDefault(user, Set.of()).stream().toList();
    }

    @Override
    public List<NotificationEntity> findPendingByUser(final Long user) {

        return USER_NOTIFICATIONS.getOrDefault(user, Set.of())
                                 .stream()
                                 .filter(n -> PENDING.equals(n.getStatus()))
                                 .toList();
    }

    @Override
    public NotificationEntity save(final NotificationEntity entity) {

        final Set<NotificationEntity> notifications = USER_NOTIFICATIONS.getOrDefault(
                entity.getUser(), new HashSet<>()
        );

        notifications.add(entity);

        USER_NOTIFICATIONS.put(entity.getUser(), notifications);

        return entity;
    }
}
