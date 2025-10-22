package com.example.tasks.repository.tasks.integration;

import com.example.tasks.repository.tasks.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ITaskJpaRepo extends JpaRepository<TaskEntity, Long> {

    @Override
    @Transactional
    <S extends TaskEntity> S saveAndFlush(final S entity);

    @Modifying
    @Transactional
    @Query(
            value = "update tasks set status = 'DELETED' where id = :id",
            nativeQuery = true
    )
    void deleteTask(@Param("id") final Long id);

    @Transactional
    List<TaskEntity> findByUser(final Long user);

    @Transactional
    @Query(
            value = "select * from tasks where \"user\" = :userId and status = (:status)::global_status",
            nativeQuery = true
    )
    List<TaskEntity> findByUserAndStatus(@Param("userId") final Long user, @Param("status") final String status);


}