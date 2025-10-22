package com.example.tasks.repository.tasks;

import com.example.tasks.exceptions.ClientException;
import com.example.tasks.repository.tasks.model.TaskEntity;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static com.example.tasks.repository.GlobalStatus.DELETED;
import static com.example.tasks.repository.GlobalStatus.PENDING;

@Repository
public class TaskRepo implements ITaskRepo {

    private static final Map<Long, Set<TaskEntity>> USER_TASKS = new HashMap<>();

    @Override
    public List<TaskEntity> findAllByUser(final Long user) {
        return USER_TASKS.getOrDefault(user, Set.of()).stream().toList();
    }

    @Override
    public List<TaskEntity> findPendingByUser(final Long user) {

        return USER_TASKS.getOrDefault(user, Set.of())
                         .stream()
                         .filter(t -> PENDING.equals(t.getStatus()))
                         .toList();
    }

    @Override
    public TaskEntity save(final TaskEntity entity) {

        final Set<TaskEntity> tasks = USER_TASKS.getOrDefault(entity.getUser(), new HashSet<>());

        tasks.add(entity);

        USER_TASKS.put(entity.getUser(), tasks);

        return entity;
    }

    @Override
    public void delete(final Long id) {

        final TaskEntity task = USER_TASKS.values()
                                          .stream()
                                          .flatMap(Collection::stream)
                                          .filter(t -> Objects.equals(t.getId(), id))
                                          .findFirst()
                                          .orElseThrow(() -> new ClientException("Задачи с таким ID не существует"));

        task.setStatus(DELETED);
    }
}
