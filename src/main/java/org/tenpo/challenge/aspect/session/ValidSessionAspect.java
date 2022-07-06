package org.tenpo.challenge.aspect.session;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.tenpo.challenge.exception.InvalidSessionException;
import org.tenpo.challenge.model.internal.SessionResponse;
import org.tenpo.challenge.service.session.SessionService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static org.tenpo.challenge.constants.ApiConstants.X_AUTH_TOKEN;

@Aspect
@Component
@Order(value = 2)
public class ValidSessionAspect {

    @Resource
    private HttpServletRequest request;

    @Resource(name = "sessionServiceImpl")
    private SessionService sessionService;

    Logger logger = LoggerFactory.getLogger(ValidSessionAspect.class);

    @Before("@annotation(org.tenpo.challenge.aspect.session.ValidSession)")
    public void validateSession(JoinPoint joinPoint) {
        SessionResponse response = this.sessionService.getSessionById(this.request.getHeader(X_AUTH_TOKEN));

        if (!response.isValid()) {
            String message = "The session id provided is invalid";
            logger.info(message);
            throw new InvalidSessionException(message);
        }
    }
}
