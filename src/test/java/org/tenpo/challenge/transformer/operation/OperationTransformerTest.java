package org.tenpo.challenge.transformer.operation;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.tenpo.challenge.constants.ApiConstants;
import org.tenpo.challenge.model.dto.operation.OperationDTO;
import org.tenpo.challenge.model.internal.Operation;

import javax.annotation.Resource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class OperationTransformerTest {

    @Resource
    private OperationTransformer operationTransformer;

    @Test
    void testTransform() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ApiConstants.DATE_TIME_DEFAULT_PATTERN);
        Operation operation = new Operation();
        operation.setCreatedAt(LocalDateTime.now());
        operation.setType("login");
        operation.setId(1L);

        OperationDTO dto = this.operationTransformer.transform(operation);
        assertEquals(operation.getId(), dto.getId());
        assertEquals(formatter.format(operation.getCreatedAt()), dto.getTransactionDate());
        assertEquals(operation.getType(), dto.getType());
    }
}