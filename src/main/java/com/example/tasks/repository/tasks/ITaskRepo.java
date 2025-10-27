package com.example.tasks.repository.tasks;

import com.example.tasks.repository.GlobalStatus;
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

    default List<Long> findOverdueIds() {
        throw new UnsupportedOperationException("Not supported yet");
    }

    default void changeStatus(final Long id, final GlobalStatus status) {
        throw new UnsupportedOperationException("Not supported yet");
    }
}
