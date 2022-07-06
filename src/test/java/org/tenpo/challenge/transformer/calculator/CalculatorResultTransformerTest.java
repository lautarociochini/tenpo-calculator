package org.tenpo.challenge.transformer.calculator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.tenpo.challenge.model.dto.calculator.CalculatorResultDTO;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CalculatorResultTransformerTest {

    @Resource
    private CalculatorResultTransformer calculatorResultTransformer;

    @Test
    void testTransform() {
        CalculatorResultDTO response = this.calculatorResultTransformer.transform(BigDecimal.valueOf(15));

        assertEquals(BigDecimal.valueOf(15).setScale(2, RoundingMode.HALF_UP), response.getResult());

    }
}