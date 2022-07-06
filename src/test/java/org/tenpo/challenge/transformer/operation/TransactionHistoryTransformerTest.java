package org.tenpo.challenge.transformer.operation;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.tenpo.challenge.model.dto.operation.TransactionHistoryDTO;
import org.tenpo.challenge.model.internal.TransactionHistory;
import org.tenpo.challenge.model.internal.Operation;

import javax.annotation.Resource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionHistoryTransformerTest {

    @Resource
    private TransactionHistoryTransformer transactionHistoryTransformer;

    @Test
    void testTransform() {
        Operation operation = new Operation();
        operation.setCreatedAt(LocalDateTime.now());
        operation.setType("login");
        operation.setId(1L);

        List<Operation> operations = new ArrayList<>();
        operations.add(operation);

        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setTotalElements(1);
        transactionHistory.setTotalPages(1);
        transactionHistory.setOperations(operations);

        TransactionHistoryDTO dto = this.transactionHistoryTransformer.transform(transactionHistory);

        assertEquals(transactionHistory.getTotalElements(), dto.getTotalElements());
        assertEquals(transactionHistory.getTotalPages(), dto.getTotalPages());
        assertEquals(transactionHistory.getOperations().size(), dto.getOperations().size());
    }
}