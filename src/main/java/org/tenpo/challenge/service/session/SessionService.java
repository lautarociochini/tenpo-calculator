package org.tenpo.challenge.service.session;

import org.tenpo.challenge.model.dto.user.AuthenticationRequest;
import org.tenpo.challenge.model.internal.SessionResponse;

import javax.servlet.http.HttpServletRequest;

public interface SessionService {

    void authenticate(HttpServletRequest request, AuthenticationRequest authenticationRequest);

    void logout(String xAuthtoken);

    SessionResponse getSessionById(String sessionId);

}
