package org.tenpo.challenge.transformer.operation;

import org.springframework.stereotype.Component;
import org.tenpo.challenge.model.dto.operation.TransactionHistoryDTO;
import org.tenpo.challenge.model.dto.operation.OperationDTO;
import org.tenpo.challenge.model.internal.TransactionHistory;
import org.tenpo.challenge.model.internal.Operation;
import org.tenpo.challenge.transformer.Transformer;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionHistoryTransformer implements Transformer<TransactionHistory, TransactionHistoryDTO> {

    @Resource(name = "operationTransformer")
    private Transformer<Operation, OperationDTO> transformer;

    @Override
    public TransactionHistoryDTO transform(TransactionHistory request) {
        List<OperationDTO> operations = request.getOperations().stream().map(
                operation -> this.transformer.transform(operation)).collect(Collectors.toCollection(ArrayList::new));

        TransactionHistoryDTO transactionHistory = new TransactionHistoryDTO();
        transactionHistory.setOperations(operations);
        transactionHistory.setTotalPages(request.getTotalPages());
        transactionHistory.setTotalElements(request.getTotalElements());
        return transactionHistory;
    }
}
