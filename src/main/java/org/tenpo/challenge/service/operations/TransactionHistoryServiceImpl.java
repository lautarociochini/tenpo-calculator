package org.tenpo.challenge.service.operations;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.tenpo.challenge.model.dao.OperationDAO;
import org.tenpo.challenge.model.dto.operation.TransactionHistoryRequest;
import org.tenpo.challenge.model.internal.TransactionHistory;
import org.tenpo.challenge.repository.TransactionHistoryRepository;
import org.tenpo.challenge.transformer.Transformer;

import javax.annotation.Resource;

@Service
public class TransactionHistoryServiceImpl implements TransactionHistoryService {

    @Resource
    private TransactionHistoryRepository transactionHistoryRepository;

    @Resource(name = "transactionHistoryDAOTransformer")
    private Transformer<Page<OperationDAO>, TransactionHistory> transformer;

    @Override
    public TransactionHistory getTransactionHistory(TransactionHistoryRequest request) {
        Pageable pageable = PageRequest.of(this.transformPage(request.getPage()), request.getPageSize());
        Page<OperationDAO> operations = this.transactionHistoryRepository.findAll(pageable);

        return this.transformer.transform(operations);
    }

    @Override
    public void addOperation(String operationType) {
        OperationDAO operationDAO = new OperationDAO();
        operationDAO.setType(operationType);

        this.transactionHistoryRepository.save(operationDAO);
    }

    private int transformPage(int page) {
        return page - 1;
    }
}
