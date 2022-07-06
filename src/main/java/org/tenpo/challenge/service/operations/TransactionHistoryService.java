package org.tenpo.challenge.service.operations;

import org.tenpo.challenge.model.dto.operation.TransactionHistoryRequest;
import org.tenpo.challenge.model.internal.TransactionHistory;

public interface TransactionHistoryService {

    TransactionHistory getTransactionHistory(TransactionHistoryRequest request);

    void addOperation(String operationType);

}
