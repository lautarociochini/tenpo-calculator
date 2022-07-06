package org.tenpo.challenge.service.session;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.session.SessionRegistry;
import org.tenpo.challenge.PostgreSQLMock;
import org.tenpo.challenge.exception.InvalidSessionException;
import org.tenpo.challenge.model.dto.user.AuthenticationRequest;
import org.tenpo.challenge.model.dto.user.SignupRequest;
import org.tenpo.challenge.model.internal.SessionResponse;
import org.tenpo.challenge.repository.UserRepository;
import org.tenpo.challenge.service.user.UserServiceImpl;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SessionServiceImplTest extends PostgreSQLMock {

    @Resource
    private SessionServiceImpl sessionService;

    @Resource
    private SessionRegistry sessionRegistry;

    @Resource
    private UserServiceImpl userService;

    @Resource
    private UserRepository userRepository;

    @BeforeAll
    void setUp() {
        this.userRepository.deleteAll();
        this.userService.signIn(new SignupRequest("john", "pass", "example_email@test.com"));
    }

    @Test
    void testAuthenticateSuccess() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        assertDoesNotThrow(() ->
                this.sessionService.authenticate(request, new AuthenticationRequest("john", "pass"))
        );
        assertNotNull(this.sessionRegistry.getSessionInformation(request.getSession().getId()));
    }

    @Test
    void testAuthenticateFailure() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        assertThrows(BadCredentialsException.class, () ->
                this.sessionService.authenticate(request, new AuthenticationRequest("john", "wrong_pass"))
        );
    }

    @Test
    void testLogoutSuccess() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        this.sessionService.authenticate(request, new AuthenticationRequest("john", "pass"));

        assertDoesNotThrow(() ->
                this.sessionService.logout(request.getSession().getId())
        );
    }

    @Test
    void testLogoutFailure() {
        assertThrows(InvalidSessionException.class, () ->
                this.sessionService.logout("auth_token_101")
        );
    }

    @Test
    void testGetSessionById_isValid() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        this.sessionService.authenticate(request, new AuthenticationRequest("john", "pass"));

        SessionResponse sessionResponse = this.sessionService.getSessionById(request.getSession().getId());

        assertNotNull(sessionResponse.getSessionInformation());
        assertTrue(sessionResponse.isValid());
    }

    @Test
    void testGetSessionById_isNotValid() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        this.sessionService.authenticate(request, new AuthenticationRequest("john", "pass"));

        SessionResponse sessionResponse = this.sessionService.getSessionById("auth_token_101");

        assertNull(sessionResponse.getSessionInformation());
        assertFalse(sessionResponse.isValid());
    }
}