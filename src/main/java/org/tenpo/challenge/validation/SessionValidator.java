package org.tenpo.challenge.validation;

import org.springframework.security.core.session.SessionInformation;
import org.springframework.stereotype.Component;

@Component
public class SessionValidator implements Validator<SessionInformation> {

    @Override
    public boolean isValid(SessionInformation sessionInformation) {
        return sessionInformation != null && !sessionInformation.isExpired();
    }
}
