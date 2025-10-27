package com.example.tasks.service.users;

import com.example.tasks.dto.request.SignInRequest;
import com.example.tasks.repository.users.model.UserEntity;

public interface IUserService {

    UserEntity signIn(final SignInRequest request);

    UserEntity signUp(final SignInRequest request);
}
