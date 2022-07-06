package org.tenpo.challenge.service.calculator;

import org.springframework.stereotype.Service;
import org.tenpo.challenge.model.dto.calculator.CalculatorRequest;

import java.math.BigDecimal;

@Service
public class CalculatorServiceImpl implements CalculatorService {

    @Override
    public BigDecimal add(CalculatorRequest request) {
        return request.getOperator1().add(request.getOperator2());
    }
}
