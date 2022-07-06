package org.tenpo.challenge.aspect.transaction;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.tenpo.challenge.service.operations.TransactionHistoryService;

import javax.annotation.Resource;

@Aspect
@Component
@Order(value = 1)
public class SaveOperationAspect {

    @Resource(name = "transactionHistoryServiceImpl")
    private TransactionHistoryService transactionHistoryService;

    @Before("@annotation(org.tenpo.challenge.aspect.transaction.SaveOperation)")
    public void saveOperation(JoinPoint joinPoint) {
        String operationType = joinPoint.getSignature().getName();
        this.transactionHistoryService.addOperation(operationType);
    }
}
