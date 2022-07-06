package org.tenpo.challenge.transformer.operation;

import org.springframework.stereotype.Component;
import org.tenpo.challenge.model.dao.OperationDAO;
import org.tenpo.challenge.model.internal.Operation;
import org.tenpo.challenge.transformer.Transformer;

@Component
public class OperationDAOTransformer implements Transformer<OperationDAO, Operation> {

    @Override
    public Operation transform(OperationDAO request) {
        Operation operation = new Operation();
        operation.setCreatedAt(request.getCreatedAt());
        operation.setType(request.getType());
        operation.setId(request.getId());

        return operation;
    }
}
