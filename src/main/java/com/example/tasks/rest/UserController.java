package com.example.tasks.rest;

import com.example.tasks.dto.request.SignInRequest;
import com.example.tasks.repository.users.model.UserEntity;
import com.example.tasks.service.users.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService iUserService;

    // POST будет корректнее, чем GET
    @PostMapping("/login")
    public ResponseEntity<UserEntity> login(@Valid @RequestBody final SignInRequest body) {
        return ok(iUserService.signIn(body));
    }

    @PostMapping
    public ResponseEntity<UserEntity> register(@Valid @RequestBody final SignInRequest body) {
        return ok(iUserService.signUp(body));
    }
}
