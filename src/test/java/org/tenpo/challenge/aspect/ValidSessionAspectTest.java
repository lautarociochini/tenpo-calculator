package org.tenpo.challenge.aspect;

import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.session.SessionInformation;
import org.tenpo.challenge.aspect.session.ValidSessionAspect;
import org.tenpo.challenge.exception.InvalidSessionException;
import org.tenpo.challenge.model.internal.SessionResponse;
import org.tenpo.challenge.service.session.SessionService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ValidSessionAspectTest {

    @InjectMocks
    private final ValidSessionAspect aspect = new ValidSessionAspect();

    @Mock
    private SessionService sessionService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private JoinPoint joinPoint;

    private SessionInformation sessionInformation;

    @BeforeEach
    void setUp() {
        sessionInformation = new SessionInformation(new Object(), "abc-123", new Date());
        Mockito.when(this.request.getHeader(Mockito.anyString())).thenReturn("abc-123");
    }

    @Test
    void testValidateSession_isNotValid() {
        SessionResponse sessionResponse = new SessionResponse(this.sessionInformation, false);
        Mockito.when(this.sessionService.getSessionById(Mockito.anyString())).thenReturn(sessionResponse);

        assertThrows(InvalidSessionException.class, () -> aspect.validateSession(this.joinPoint));
    }

    @Test
    void testValidateSession_isValid() {
        SessionResponse sessionResponse = new SessionResponse(this.sessionInformation, true);
        Mockito.when(this.sessionService.getSessionById(Mockito.anyString())).thenReturn(sessionResponse);

        assertDoesNotThrow(() -> aspect.validateSession(this.joinPoint));
    }
}