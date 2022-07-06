package org.tenpo.challenge.service.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;
import org.tenpo.challenge.exception.InvalidSessionException;
import org.tenpo.challenge.model.dto.user.AuthenticationRequest;
import org.tenpo.challenge.model.internal.SessionResponse;
import org.tenpo.challenge.validation.SessionValidator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Service
public class SessionServiceImpl implements SessionService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private SessionRegistry sessionRegistry;

    @Resource
    private SessionValidator sessionValidator;

    Logger logger = LoggerFactory.getLogger(SessionServiceImpl.class);

    @Override
    public void authenticate(HttpServletRequest request, AuthenticationRequest authenticationRequest) {
        try {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());
            this.authenticationManager.authenticate(authentication);
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            HttpSession session = request.getSession(true);
            session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, context);
            this.sessionRegistry.registerNewSession(request.getSession().getId(), authentication.getPrincipal());
        } catch (BadCredentialsException e) {
            String message = "Invalid username or password";
            logger.info(message);
            throw new BadCredentialsException(message, e);
        }
    }

    @Override
    public void logout(String xAuthToken) {
        SessionResponse response = this.getSessionById(xAuthToken);

        if (!response.isValid()) {
            String message = "The session id provided is invalid";
            logger.info(message);
            throw new InvalidSessionException(message);
        }

        response.getSessionInformation().expireNow();
    }

    @Override
    public SessionResponse getSessionById(String sessionId) {
        SessionInformation sessionInformation = this.sessionRegistry.getSessionInformation(sessionId);
        boolean isValid = this.sessionValidator.isValid(sessionInformation);

        return new SessionResponse(sessionInformation, isValid);
    }
}
