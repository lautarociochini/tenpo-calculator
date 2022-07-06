package org.tenpo.challenge.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.tenpo.challenge.aspect.transaction.SaveOperationAspect;
import org.tenpo.challenge.service.operations.TransactionHistoryService;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

@SpringBootTest
class SaveOperationAspectTest {

    @InjectMocks
    private final SaveOperationAspect aspect = new SaveOperationAspect();

    @Mock
    private TransactionHistoryService transactionHistoryService;

    @Mock
    private JoinPoint joinPoint;

    @BeforeEach
    void setUp() {
        MethodSignature signature = mock(MethodSignature.class);
        Mockito.when(this.joinPoint.getSignature()).thenReturn(signature);
        Mockito.when(this.joinPoint.getSignature().getName()).thenReturn("login");
    }

    @Test
    void testSaveOperation_isValid() {
        assertDoesNotThrow(() -> this.aspect.saveOperation(this.joinPoint));
    }
}