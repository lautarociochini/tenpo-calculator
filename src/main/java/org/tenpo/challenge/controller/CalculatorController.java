package org.tenpo.challenge.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tenpo.challenge.aspect.session.ValidSession;
import org.tenpo.challenge.aspect.transaction.SaveOperation;
import org.tenpo.challenge.model.dto.calculator.CalculatorRequest;
import org.tenpo.challenge.model.dto.calculator.CalculatorResultDTO;
import org.tenpo.challenge.service.calculator.CalculatorService;
import org.tenpo.challenge.transformer.Transformer;

import javax.annotation.Resource;
import javax.validation.Valid;

import java.math.BigDecimal;

import static org.tenpo.challenge.constants.ApiConstants.X_AUTH_TOKEN;

@RestController
@RequestMapping("v1/calculator")
public class CalculatorController {

    @Resource(name = "calculatorServiceImpl")
    private CalculatorService calculatorService;

    @Resource(name = "calculatorResultTransformer")
    private Transformer<BigDecimal, CalculatorResultDTO> transformer;

    @SaveOperation
    @ValidSession
    @PostMapping("/add")
    @ApiOperation(value = "Perform two numbers addition and returns result with scale (2). Only authenticated users can access", response = CalculatorResultDTO.class)
    public CalculatorResultDTO add(@Valid @RequestBody CalculatorRequest request, @RequestHeader(value= X_AUTH_TOKEN) String xAuthToken) {
        BigDecimal result = this.calculatorService.add(request);
        return this.transformer.transform(result);
    }
}

