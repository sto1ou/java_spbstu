package com.example.tasks.service.tasks;

import com.example.tasks.dto.request.TaskCreateRequest;
import com.example.tasks.repository.GlobalStatus;
import com.example.tasks.repository.tasks.model.TaskEntity;

import java.util.List;

public interface ITaskService {

    List<TaskEntity> findAllByUser(final Long user);

    List<TaskEntity> findPendingByUser(final Long user);

    TaskEntity save(final TaskCreateRequest request) throws Exception;

    void delete(final Long id);

    List<Long> findOverdueIds();

    void changeStatus(final Long id, final GlobalStatus status);
}
