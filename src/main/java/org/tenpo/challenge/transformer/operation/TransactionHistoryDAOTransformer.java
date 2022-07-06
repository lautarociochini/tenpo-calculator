package org.tenpo.challenge.transformer.operation;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.tenpo.challenge.model.dao.OperationDAO;
import org.tenpo.challenge.model.internal.TransactionHistory;
import org.tenpo.challenge.model.internal.Operation;
import org.tenpo.challenge.transformer.Transformer;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionHistoryDAOTransformer implements Transformer<Page<OperationDAO>, TransactionHistory> {

    @Resource(name = "operationDAOTransformer")
    private Transformer<OperationDAO, Operation> transformer;

    @Override
    public TransactionHistory transform(Page<OperationDAO> request) {
        List<Operation> operations = request.get().map(operationDAO
                -> this.transformer.transform(operationDAO)).collect(Collectors.toCollection(ArrayList::new));

        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setOperations(operations);
        transactionHistory.setTotalPages(request.getTotalPages());
        transactionHistory.setTotalElements(request.getTotalElements());
        return transactionHistory;
    }
}
