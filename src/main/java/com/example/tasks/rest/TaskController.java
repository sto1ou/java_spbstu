package com.example.tasks.rest;

import com.example.tasks.dto.request.TaskCreateRequest;
import com.example.tasks.repository.tasks.model.TaskEntity;
import com.example.tasks.service.tasks.ITaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskService iTaskService;

    @GetMapping
    public ResponseEntity<List<TaskEntity>> getUserTasks(@RequestParam("user") final Long user) {
        return ok(iTaskService.findAllByUser(user));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<TaskEntity>> getPendingUserTasks(@RequestParam("user") final Long user) {
        return ok(iTaskService.findPendingByUser(user));
    }

    @PostMapping
    public ResponseEntity<TaskEntity> create(@Valid @RequestBody final TaskCreateRequest body) throws Exception {
        return status(CREATED).body(iTaskService.save(body));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") final Long id) {

        iTaskService.delete(id);

        return noContent().build();
    }

}
