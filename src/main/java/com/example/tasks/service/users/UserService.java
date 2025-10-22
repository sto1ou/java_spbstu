package com.example.tasks.service.users;

import com.example.tasks.dto.request.SignInRequest;
import com.example.tasks.repository.users.IUserRepo;
import com.example.tasks.repository.users.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepo iUserRepo;

    @Override
    public UserEntity signIn(SignInRequest request) {
        return iUserRepo.signIn(request);
    }

    @Override
    public UserEntity signUp(SignInRequest request) {
        return iUserRepo.save(request);
    }
}
