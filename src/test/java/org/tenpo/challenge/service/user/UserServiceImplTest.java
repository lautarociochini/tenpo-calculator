package org.tenpo.challenge.service.user;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.tenpo.challenge.PostgreSQLMock;
import org.tenpo.challenge.exception.DuplicatedUserException;
import org.tenpo.challenge.model.dto.user.SignupRequest;
import org.tenpo.challenge.model.internal.User;
import org.tenpo.challenge.repository.UserRepository;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest extends PostgreSQLMock {

    @Resource
    private UserServiceImpl userService;

    @Resource
    private UserRepository userRepository;

    @BeforeAll
    void setUp() {
        this.userRepository.deleteAll();
        this.userService.signIn(new SignupRequest("john", "pass", "example_mail@test.com"));
    }

    @Test
    void testSignIn_newUser() {
        User user = this.userService.signIn(new SignupRequest("peter", "pass", "new_email@test.com"));

        assertNotNull(user);
    }

    @Test
    void testSignIn_userAlreadyExists_shouldThrowException() {
        assertThrows(DuplicatedUserException.class, () ->
                this.userService.signIn(new SignupRequest("john", "pass", "example_mail@test.com"))
        );
    }

    @Test
    void testFindUserByUsername_userExists() {
        User user = this.userService.findUserByUsername("john");

        assertNotNull(user);
    }

    @Test
    void testFindUserByUsername_userNotExists() {
        User user = this.userService.findUserByUsername("tom");

        assertNull(user);
    }
}