package com.example.tasks.service.tasks;

import com.example.tasks.dto.request.TaskCreateRequest;
import com.example.tasks.mq.MessageSender;
import com.example.tasks.repository.GlobalStatus;
import com.example.tasks.repository.tasks.ITaskRepo;
import com.example.tasks.repository.tasks.model.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static java.lang.System.currentTimeMillis;

@Service
public class TaskService implements ITaskService {

    private static final AtomicLong ID = new AtomicLong(1L);

    @Autowired
    private ITaskRepo iTaskRepo;

    @Autowired
    private MessageSender messageSender;

    @Override
    @Cacheable(value = "all_tasks", key = "#user", condition = "#user != null")
    public List<TaskEntity> findAllByUser(final Long user) {
        return iTaskRepo.findAllByUser(user);
    }

    @Override
    @Cacheable(value = "pending_tasks", key = "#user", condition = "#user != null")
    public List<TaskEntity> findPendingByUser(final Long user) {
        return iTaskRepo.findPendingByUser(user);
    }

    @Override
    @CacheEvict(value = {"all_tasks", "pending_tasks"}, allEntries = true)
    public TaskEntity save(final TaskCreateRequest r) throws Exception {

        final TaskEntity task = iTaskRepo.save(
                TaskEntity.builder()
                          .id(getNextId())
                          .name(r.getName())
                          .status(GlobalStatus.getValue(r.getStatus()))
                          .target(r.getTarget().toInstant(ZoneOffset.UTC).toEpochMilli())
                          .user(r.getUser())
                          .created(currentTimeMillis())
                          .build()
        );

        messageSender.send(task);

        return task;
    }

    @Override
    @CacheEvict(value = {"all_tasks", "pending_tasks"}, allEntries = true)
    public void delete(final Long id) {
        iTaskRepo.delete(id);
    }

    @Override
    public List<Long> findOverdueIds() {
        return iTaskRepo.findOverdueIds();
    }

    @Override
    @CacheEvict(value = {"all_tasks", "pending_tasks"}, allEntries = true)
    public void changeStatus(final Long id, final GlobalStatus status) {
        iTaskRepo.changeStatus(id, status);
    }

    private static long getNextId() {
        return ID.getAndIncrement();
    }
}
