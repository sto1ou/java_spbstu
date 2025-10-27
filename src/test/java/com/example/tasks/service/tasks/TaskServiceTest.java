package com.example.tasks.service.tasks;

import com.example.tasks.dto.request.TaskCreateRequest;
import com.example.tasks.repository.tasks.ITaskRepo;
import com.example.tasks.repository.tasks.model.TaskEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static com.example.tasks.repository.GlobalStatus.PENDING;
import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private ITaskRepo iTaskRepo;

    @InjectMocks
    private TaskService taskService;

    @Test
    void save() {

        final LocalDateTime target = now();

        final TaskCreateRequest given = new TaskCreateRequest("test", target, 1L, null);

        when(iTaskRepo.save(any(TaskEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        final TaskEntity result = taskService.save(given);

        assertEquals(target.toInstant(ZoneOffset.UTC).toEpochMilli(), result.getTarget());
        assertEquals(given.getName(), result.getName());
        assertEquals(given.getUser(), result.getUser());
        assertEquals(PENDING, result.getStatus());
    }
}