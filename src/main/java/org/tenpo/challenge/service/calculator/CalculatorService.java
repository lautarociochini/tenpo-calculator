package org.tenpo.challenge.service.calculator;

import org.tenpo.challenge.model.dto.calculator.CalculatorRequest;

import java.math.BigDecimal;

public interface CalculatorService {

    BigDecimal add(CalculatorRequest request);

}
