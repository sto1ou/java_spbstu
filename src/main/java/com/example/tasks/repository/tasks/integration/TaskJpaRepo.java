package com.example.tasks.repository.tasks.integration;

import com.example.tasks.repository.GlobalStatus;
import com.example.tasks.repository.tasks.ITaskRepo;
import com.example.tasks.repository.tasks.model.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.tasks.repository.GlobalStatus.PENDING;

@Profile("!mem")
@Repository
public class TaskJpaRepo implements ITaskRepo {

    @Autowired
    private ITaskJpaRepo iTaskJpaRepo;

    @Override
    public List<TaskEntity> findAllByUser(final Long user) {
        return iTaskJpaRepo.findByUser(user);
    }

    @Override
    public List<TaskEntity> findPendingByUser(final Long user) {
        return iTaskJpaRepo.findByUserAndStatus(user, PENDING.name());
    }

    @Override
    public TaskEntity save(final TaskEntity entity) {
        return iTaskJpaRepo.saveAndFlush(entity);
    }

    @Override
    public void delete(final Long id) {
        iTaskJpaRepo.deleteTask(id);
    }

    @Override
    public List<Long> findOverdueIds() {
        return iTaskJpaRepo.findOverdueIdList();
    }

    @Override
    public void changeStatus(final Long id, final GlobalStatus status) {
        iTaskJpaRepo.changeStatus(id, status.name());
    }
}
