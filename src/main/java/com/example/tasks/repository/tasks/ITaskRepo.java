package com.example.tasks.repository.tasks;

import com.example.tasks.repository.tasks.model.TaskEntity;

import java.util.List;

/**
 * Общий интерфейс
 */
public interface ITaskRepo {

    List<TaskEntity> findAllByUser(final Long user);

    List<TaskEntity> findPendingByUser(final Long user);

    TaskEntity save(final TaskEntity entity);

    void delete(final Long id);
}
