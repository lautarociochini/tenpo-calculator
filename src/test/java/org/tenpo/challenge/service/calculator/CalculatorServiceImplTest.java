package org.tenpo.challenge.service.calculator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.tenpo.challenge.model.dto.calculator.CalculatorRequest;

import javax.annotation.Resource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CalculatorServiceImplTest {

    @Resource()
    private CalculatorServiceImpl calculatorService;

    @Test
    void testAdd() {
        BigDecimal result = this.calculatorService.add(new CalculatorRequest(BigDecimal.valueOf(25), BigDecimal.valueOf(15)));

        assertEquals(BigDecimal.valueOf(40), result);
    }
}