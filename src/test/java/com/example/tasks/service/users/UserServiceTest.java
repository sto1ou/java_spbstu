package com.example.tasks.service.users;

import com.example.tasks.dto.request.SignInRequest;
import com.example.tasks.repository.users.IUserRepo;
import com.example.tasks.repository.users.model.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.tasks.utils.PasswordUtils.md5;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final Long ID = 1L;
    private static final Long CREATED = 0L;
    private static final String CRED = "test";

    @Mock
    private IUserRepo iUserRepo;

    @InjectMocks
    private UserService userService;

    @Test
    void signIn() {

        final SignInRequest given = new SignInRequest(CRED, CRED);

        when(iUserRepo.signIn(given)).thenAnswer(i -> {

            final SignInRequest r = (SignInRequest)i.getArguments()[0];
            return new UserEntity(ID, r.getUsername(), md5(r.getPassword()), CREATED);
        });

        final UserEntity expected = new UserEntity(ID, given.getUsername(), md5(given.getPassword()), CREATED);

        assertEquals(expected, userService.signIn(given));
    }
}