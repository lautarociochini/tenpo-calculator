package org.tenpo.challenge.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.tenpo.challenge.PostgreSQLMock;
import org.tenpo.challenge.exception.InvalidSessionException;
import org.tenpo.challenge.exception.DuplicatedUserException;
import org.tenpo.challenge.model.dto.user.AuthenticationRequest;
import org.tenpo.challenge.model.dto.user.SignupRequest;
import org.tenpo.challenge.repository.UserRepository;
import org.tenpo.challenge.service.user.UserServiceImpl;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest extends PostgreSQLMock {

    @Resource
    private UserController userController;

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
    void testSignupSuccess() {
        assertDoesNotThrow(() ->
                this.userController.signup(new SignupRequest("peter", "pass", "example_mail@test.com"))
        );
    }

    @Test
    void testSignupFailure() {
        assertThrows(DuplicatedUserException.class, () ->
                this.userController.signup(new SignupRequest("john", "pass", "example_mail@test.com"))
        );
    }

    @Test
    void testLoginSuccess() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        assertDoesNotThrow(() ->
                this.userController.login(new AuthenticationRequest("john", "pass"), request)
        );
    }

    @Test
    void testLoginFailure() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        assertThrows(BadCredentialsException.class, () ->
                this.userController.login(new AuthenticationRequest("john", "wrong_pass"), request)
        );
    }

    @Test
    void testLogoutSuccess() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        this.userController.login(new AuthenticationRequest("john", "pass"), request);

        assertDoesNotThrow(() ->
                this.userController.logout(request.getSession().getId())
        );
    }

    @Test
    void testLogoutFailure() {
        assertThrows(InvalidSessionException.class, () ->
                this.userController.logout("auth_token_101")
        );
    }
}