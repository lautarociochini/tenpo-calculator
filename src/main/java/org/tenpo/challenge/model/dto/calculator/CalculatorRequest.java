package org.tenpo.challenge.model.dto.calculator;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CalculatorRequest {

    @NotNull(message = "operator1 must not be null")
    private final BigDecimal operator1;

    @NotNull(message = "operator2 must not be null")
    private final BigDecimal operator2;

    public CalculatorRequest(BigDecimal operator1, BigDecimal operator2) {
        this.operator1 = operator1;
        this.operator2 = operator2;
    }

    public BigDecimal getOperator1() {
        return operator1;
    }

    public BigDecimal getOperator2() {
        return operator2;
    }
}
