package com.example.tasks.service.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TaskValidityTimeCheckingService {

    @Autowired
    private ITaskService iTaskService;

    @Autowired
    private TaskOverdueService taskOverdueService;

    /**
     * Проверяет задачи каждую минуту
     */
    @Scheduled(cron = "0 * * * * ?")
    public void checkTasksValidityTime() {
        iTaskService.findOverdueIds().forEach(taskOverdueService::consumeOverdueTaskId);
    }
}
