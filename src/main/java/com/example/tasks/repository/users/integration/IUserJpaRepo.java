package com.example.tasks.repository.users.integration;

import com.example.tasks.repository.users.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface IUserJpaRepo extends JpaRepository<UserEntity, Long> {

    @Transactional
    Optional<UserEntity> findByLogin(final String login);

    @Override
    @Transactional
    <S extends UserEntity> S save(final S entity);
}