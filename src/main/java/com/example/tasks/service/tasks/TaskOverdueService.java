package com.example.tasks.service.tasks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.example.tasks.repository.GlobalStatus.FAIL;

@Slf4j
@Service
public class TaskOverdueService {

    @Autowired
    private ITaskService iTaskService;

    /**
     * Логирует и фейлит просроченную задачу
     *
     * @param id task id
     */
    @Async
    void consumeOverdueTaskId(final Long id) {

        log.warn("Task with id = {} is overdue. Marking as failed.", id);

        iTaskService.changeStatus(id, FAIL);
    }
}
