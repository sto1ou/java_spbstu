package com.example.tasks.repository.users;

import com.example.tasks.dto.request.SignInRequest;
import com.example.tasks.exceptions.ClientException;
import com.example.tasks.repository.users.model.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static com.example.tasks.utils.PasswordUtils.matches;
import static com.example.tasks.utils.PasswordUtils.md5;
import static java.lang.System.currentTimeMillis;
import static java.util.Optional.ofNullable;

@Repository
public class UserRepo implements IUserRepo {

    private static final AtomicLong ID = new AtomicLong(1L);
    private static final Map<String, UserEntity> USERS = new HashMap<>();

    @Override
    public UserEntity signIn(final SignInRequest request) {

        final UserEntity user = ofNullable(USERS.get(request.getUsername()))
                .orElseThrow(() -> new ClientException("Неверный логин или пароль"));

        if (matches(request.getPassword(), user.getPassword())) {
            return user;
        }

        throw new ClientException("Неверный логин или пароль");
    }

    @Override
    public UserEntity save(final SignInRequest request) {

        final String username = request.getUsername();

        if (USERS.containsKey(username)) {
            throw new ClientException("Пользователь уже зарегистрирован в системе");
        }

        final UserEntity user = UserEntity
                .builder()
                .id(getNextId())
                .login(username)
                .password(md5(request.getPassword()))
                .created(currentTimeMillis())
                .build();

        USERS.put(username, user);

        return user;
    }

    private static long getNextId() {
        return ID.getAndIncrement();
    }
}
