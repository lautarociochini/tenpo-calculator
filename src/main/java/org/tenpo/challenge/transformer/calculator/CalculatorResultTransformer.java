package org.tenpo.challenge.transformer.calculator;

import org.springframework.stereotype.Component;
import org.tenpo.challenge.model.dto.calculator.CalculatorResultDTO;
import org.tenpo.challenge.transformer.Transformer;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class CalculatorResultTransformer implements Transformer<BigDecimal, CalculatorResultDTO> {

    @Override
    public CalculatorResultDTO transform(BigDecimal result) {
        CalculatorResultDTO calculatorResultDTO = new CalculatorResultDTO();
        calculatorResultDTO.setResult(round(result));
        return calculatorResultDTO;
    }

    private static BigDecimal round(BigDecimal val) {
        return new BigDecimal(val.toString()).setScale(2, RoundingMode.HALF_UP);
    }
}
