package org.tenpo.challenge;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;
import org.tenpo.challenge.controller.TransactionHistoryController;
import org.tenpo.challenge.controller.CalculatorController;
import org.tenpo.challenge.controller.UserController;
import org.tenpo.challenge.model.dto.operation.TransactionHistoryDTO;
import org.tenpo.challenge.model.dto.calculator.CalculatorRequest;
import org.tenpo.challenge.model.dto.calculator.CalculatorResultDTO;
import org.tenpo.challenge.model.dto.user.AuthenticationRequest;
import org.tenpo.challenge.model.dto.user.SignupRequest;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.tenpo.challenge.constants.ApiConstants.X_AUTH_TOKEN;

@SpringBootTest
class ApplicationFlowTest extends PostgreSQLMock {

    @Resource
    private TransactionHistoryController transactionHistoryController;

    @Resource
    private UserController userController;

    @Resource
    private CalculatorController calculatorController;

    @Test
    void testFlowSuccess() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        assertDoesNotThrow(() -> {
                    this.userController.signup(new SignupRequest("test", "pass", "example_email@test.com"));
                    this.userController.login(new AuthenticationRequest("test", "pass"), request);
                }
        );

        request.addHeader(X_AUTH_TOKEN, request.getSession().getId());
        RequestContextHolder.setRequestAttributes(new ServletWebRequest(request));

        CalculatorRequest calculatorRequest = new CalculatorRequest(BigDecimal.valueOf(10), BigDecimal.valueOf(5));
        CalculatorResultDTO calculatorResultDTO = this.calculatorController.add(calculatorRequest, "auth_token_101");
        assertEquals(BigDecimal.valueOf(15).setScale(2, RoundingMode.HALF_UP), calculatorResultDTO.getResult());

        assertDoesNotThrow(() ->
                this.userController.logout(request.getSession().getId())
        );

        TransactionHistoryDTO transactionHistoryDTO = this.transactionHistoryController.findAll(1, 25);
        assertNotNull(transactionHistoryDTO);
    }
}
