package org.tenpo.challenge.validation;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.session.SessionInformation;

import javax.annotation.Resource;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SessionValidatorTest {

    @Resource
    private SessionValidator sessionValidator;

    @Test
    void testIsValidSuccess() {
        SessionInformation sessionInformation = new SessionInformation(new Object(), "12345", new Date());

        assertTrue(this.sessionValidator.isValid(sessionInformation));
    }

    @Test
    void testIsValidFailure() {
        SessionInformation sessionInformation = new SessionInformation(new Object(), "12345", new Date());
        sessionInformation.expireNow();
        assertFalse(this.sessionValidator.isValid(sessionInformation));
    }

    @Test
    void testIsValid_withoutSessionInformation() {
        assertFalse(this.sessionValidator.isValid(null));
    }
}