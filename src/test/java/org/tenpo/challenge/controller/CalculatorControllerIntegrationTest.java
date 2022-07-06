package org.tenpo.challenge.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;
import org.tenpo.challenge.PostgreSQLMock;
import org.tenpo.challenge.exception.InvalidSessionException;
import org.tenpo.challenge.model.dto.calculator.CalculatorRequest;
import org.tenpo.challenge.model.dto.calculator.CalculatorResultDTO;
import org.tenpo.challenge.model.dto.user.AuthenticationRequest;
import org.tenpo.challenge.model.dto.user.SignupRequest;
import org.tenpo.challenge.repository.UserRepository;
import org.tenpo.challenge.service.session.SessionService;
import org.tenpo.challenge.service.user.UserService;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;
import static org.tenpo.challenge.constants.ApiConstants.X_AUTH_TOKEN;

@SpringBootTest
class CalculatorControllerIntegrationTest extends PostgreSQLMock {

    @Resource
    private CalculatorController calculatorController;

    @Resource
    private UserRepository userRepository;

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @Resource(name = "sessionServiceImpl")
    private SessionService sessionService;

    @BeforeAll
    void setUp() {
        this.userRepository.deleteAll();
        this.userService.signIn(new SignupRequest("john", "pass", "example_email@test.com"));
    }

    @Test
    void testAddAuthenticatedUser() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        this.sessionService.authenticate(request, new AuthenticationRequest("john", "pass"));
        request.addHeader(X_AUTH_TOKEN, request.getSession().getId());
        RequestContextHolder.setRequestAttributes(new ServletWebRequest(request));

        CalculatorResultDTO calculatorResultDTO = this.calculatorController.add(new CalculatorRequest(BigDecimal.valueOf(1), BigDecimal.valueOf(2)), "1234");

        assertNotNull(calculatorResultDTO);
        assertEquals(BigDecimal.valueOf(3).setScale(2, RoundingMode.HALF_UP), calculatorResultDTO.getResult());
    }

    @Test
    void testAddNotAuthenticatedUser_shouldThrowException() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(X_AUTH_TOKEN, "auth_token_101");
        RequestContextHolder.setRequestAttributes(new ServletWebRequest(request));

        assertThrows(InvalidSessionException.class, () -> this.calculatorController.add(new CalculatorRequest(BigDecimal.valueOf(1), BigDecimal.valueOf(2)), "1234"));
    }
}