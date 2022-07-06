package org.tenpo.challenge.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.tenpo.challenge.PostgreSQLMock;
import org.tenpo.challenge.model.dao.OperationDAO;
import org.tenpo.challenge.model.dto.operation.TransactionHistoryDTO;
import org.tenpo.challenge.repository.TransactionHistoryRepository;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;
import static org.tenpo.challenge.TestHelper.createOperationDAO;

@SpringBootTest
class TransactionHistoryControllerIntegrationTest extends PostgreSQLMock {

    @Resource
    private TransactionHistoryController transactionHistoryController;

    @Resource
    private TransactionHistoryRepository transactionHistoryRepository;

    @BeforeAll
    void setUp() {
        this.transactionHistoryRepository.deleteAll();
        OperationDAO operationDAO1 = createOperationDAO("signup");
        OperationDAO operationDAO2 = createOperationDAO("login");
        OperationDAO operationDAO3 = createOperationDAO("add");
        OperationDAO operationDAO4 = createOperationDAO("logout");
        this.transactionHistoryRepository.save(operationDAO1);
        this.transactionHistoryRepository.save(operationDAO2);
        this.transactionHistoryRepository.save(operationDAO3);
        this.transactionHistoryRepository.save(operationDAO4);
    }

    @Test
    void testFindAll() {
        TransactionHistoryDTO dto = this.transactionHistoryController.findAll(1, 1);

        assertNotNull(dto);
        assertEquals(5L, dto.getTotalElements());
        assertEquals(5, dto.getTotalPages());
        assertEquals(1, dto.getOperations().size());
    }

}