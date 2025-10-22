package com.example.tasks.repository.users;

import com.example.tasks.dto.request.SignInRequest;
import com.example.tasks.repository.users.model.UserEntity;

/**
 * Общий интерфейс
 */
public interface IUserRepo {

    UserEntity signIn(final SignInRequest request);

    UserEntity save(final SignInRequest request);
}
