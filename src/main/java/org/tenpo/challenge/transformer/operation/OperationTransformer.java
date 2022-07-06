package org.tenpo.challenge.transformer.operation;

import org.springframework.stereotype.Component;
import org.tenpo.challenge.model.dto.operation.OperationDTO;
import org.tenpo.challenge.model.internal.Operation;
import org.tenpo.challenge.transformer.Transformer;

import java.time.format.DateTimeFormatter;

import static org.tenpo.challenge.constants.ApiConstants.DATE_TIME_DEFAULT_PATTERN;

@Component
public class OperationTransformer implements Transformer<Operation, OperationDTO> {

    @Override
    public OperationDTO transform(Operation request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_DEFAULT_PATTERN);
        OperationDTO operation = new OperationDTO();
        operation.setTransactionDate(formatter.format(request.getCreatedAt()));
        operation.setType(request.getType());
        operation.setId(request.getId());

        return operation;
    }
}
