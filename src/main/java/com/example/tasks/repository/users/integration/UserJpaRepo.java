package com.example.tasks.repository.users.integration;

import com.example.tasks.dto.request.SignInRequest;
import com.example.tasks.exceptions.ClientException;
import com.example.tasks.repository.users.IUserRepo;
import com.example.tasks.repository.users.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import static com.example.tasks.utils.PasswordUtils.matches;
import static com.example.tasks.utils.PasswordUtils.md5;
import static java.lang.System.currentTimeMillis;

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

        final String username = request.getUsername();

        iUserJpaRepo.findByLogin(request.getUsername()).ifPresent(u -> {
            throw new ClientException("Пользователь уже зарегистрирован в системе");
        });

        final UserEntity user = UserEntity
                .builder()
                .login(username)
                .password(md5(request.getPassword()))
                .created(currentTimeMillis())
                .build();

        iUserJpaRepo.save(user);

        return user;
    }
}
