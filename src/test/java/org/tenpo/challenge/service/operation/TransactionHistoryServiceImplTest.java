package org.tenpo.challenge.service.operation;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.tenpo.challenge.PostgreSQLMock;
import org.tenpo.challenge.model.dao.OperationDAO;
import org.tenpo.challenge.model.dto.operation.TransactionHistoryRequest;
import org.tenpo.challenge.model.internal.TransactionHistory;
import org.tenpo.challenge.repository.TransactionHistoryRepository;
import org.tenpo.challenge.service.operations.TransactionHistoryServiceImpl;

import javax.annotation.Resource;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.tenpo.challenge.TestHelper.createOperationDAO;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TransactionHistoryServiceImplTest extends PostgreSQLMock {

    @Resource
    private TransactionHistoryServiceImpl transactionHistoryService;

    @Resource
    private TransactionHistoryRepository transactionHistoryRepository;

    @BeforeAll
    public void setup() {
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
    @Order(1)
    void testGetTransactionHistory_firstPage_withTwoElementsEach() {
        TransactionHistory transactionHistory = this.transactionHistoryService.getTransactionHistory(new TransactionHistoryRequest(1, 2));

        assertEquals(4, transactionHistory.getTotalElements());
        assertEquals(2, transactionHistory.getTotalPages());
        assertEquals(2, transactionHistory.getOperations().size());
    }

    @Test
    @Order(2)
    void testGetTransactionHistory_secondPage_withThreeElementsEach() {
        TransactionHistory transactionHistory = this.transactionHistoryService.getTransactionHistory(new TransactionHistoryRequest(2, 3));

        assertEquals(4, transactionHistory.getTotalElements());
        assertEquals(2, transactionHistory.getTotalPages());
        assertEquals(1, transactionHistory.getOperations().size());
    }

    @Test
    @Order(3)
    void testGetTransactionHistory_firstPage_withOneElementEach() {
        TransactionHistory transactionHistory = this.transactionHistoryService.getTransactionHistory(new TransactionHistoryRequest(1, 1));

        assertEquals(4, transactionHistory.getTotalElements());
        assertEquals(4, transactionHistory.getTotalPages());
        assertEquals(1, transactionHistory.getOperations().size());
    }

    @Test
    @Order(4)
    void testInsertOperationToHistory() {
        this.transactionHistoryService.addOperation("test");

        Iterable<OperationDAO> elements = this.transactionHistoryRepository.findAll();
        boolean isOperationSaved = StreamSupport.stream(elements.spliterator(), false)
                .anyMatch(operation -> operation.getType().equals("test"));

        assertTrue(isOperationSaved);
    }
}