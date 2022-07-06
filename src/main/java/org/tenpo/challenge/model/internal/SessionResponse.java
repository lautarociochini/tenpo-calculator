package org.tenpo.challenge.model.internal;

import org.springframework.security.core.session.SessionInformation;

public class SessionResponse {

    private final SessionInformation sessionInformation;
    private final boolean isValid;

    public SessionResponse(SessionInformation sessionInformation, boolean isValid) {
        this.sessionInformation = sessionInformation;
        this.isValid = isValid;
    }

    public SessionInformation getSessionInformation() {
        return sessionInformation;
    }

    public boolean isValid() {
        return isValid;
    }
}
