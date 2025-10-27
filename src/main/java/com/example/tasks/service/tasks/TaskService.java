package com.example.tasks.service.tasks;

import com.example.tasks.dto.request.TaskCreateRequest;
import com.example.tasks.repository.GlobalStatus;
import com.example.tasks.repository.tasks.ITaskRepo;
import com.example.tasks.repository.tasks.model.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public List<TaskEntity> findAllByUser(final Long user) {
        return iTaskRepo.findAllByUser(user);
    }

    @Override
    public List<TaskEntity> findPendingByUser(final Long user) {
        return iTaskRepo.findPendingByUser(user);
    }

    @Override
    public TaskEntity save(final TaskCreateRequest request) {

        final TaskEntity task = TaskEntity.builder()
                                          .id(getNextId())
                                          .name(request.getName())
                                          .status(GlobalStatus.getValue(request.getStatus()))
                                          .target(request.getTarget().toInstant(ZoneOffset.UTC).toEpochMilli())
                                          .user(request.getUser())
                                          .created(currentTimeMillis())
                                          .build();

        return iTaskRepo.save(task);
    }

    @Override
    public void delete(final Long id) {
        iTaskRepo.delete(id);
    }

    private static long getNextId() {
        return ID.getAndIncrement();
    }
}
