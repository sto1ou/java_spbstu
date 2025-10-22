package com.example.tasks.repository.users.integration;

import com.example.tasks.dto.request.SignInRequest;
import com.example.tasks.exceptions.ClientException;
import com.example.tasks.repository.users.IUserRepo;
import com.example.tasks.repository.users.model.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.example.tasks.utils.PasswordUtils.matches;
import static com.example.tasks.utils.PasswordUtils.md5;
import static java.lang.System.currentTimeMillis;

@Slf4j
@Profile("!mem")
@Repository
public class UserJpaRepo implements IUserRepo {

    @Autowired
    private IUserJpaRepo iUserJpaRepo;

    @Override
    public UserEntity signIn(final SignInRequest request) {

        final UserEntity user = iUserJpaRepo.findByLogin(request.getUsername())
                                            .orElseThrow(() -> new ClientException("Неверный логин или пароль"));

        if (matches(request.getPassword(), user.getPassword())) {
            return user;
        }

        throw new ClientException("Неверный логин или пароль");
    }

    @Override
    public UserEntity save(final SignInRequest request) {

        log.info("saving: {}", request);

        final String username = request.getUsername();

        final Optional<UserEntity> entity = iUserJpaRepo.findByLogin(request.getUsername());

        if (entity.isPresent()) {
            throw new ClientException("Пользователь уже зарегистрирован в системе");
        }

        return iUserJpaRepo.save(UserEntity
                .builder()
                .login(username)
                .password(md5(request.getPassword()))
                .created(currentTimeMillis())
                .build());
    }
}
