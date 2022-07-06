package org.tenpo.challenge.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tenpo.challenge.aspect.transaction.SaveOperation;
import org.tenpo.challenge.model.dto.operation.TransactionHistoryDTO;
import org.tenpo.challenge.model.dto.operation.TransactionHistoryRequest;
import org.tenpo.challenge.model.internal.TransactionHistory;
import org.tenpo.challenge.service.operations.TransactionHistoryService;
import org.tenpo.challenge.transformer.Transformer;

import javax.annotation.Resource;

import static org.tenpo.challenge.constants.ApiConstants.*;


@RestController
@RequestMapping("v1/operation")
public class TransactionHistoryController {

    @Resource(name = "transactionHistoryServiceImpl")
    private TransactionHistoryService transactionHistoryService;

    @Resource(name = "transactionHistoryTransformer")
    private Transformer<TransactionHistory, TransactionHistoryDTO> transactionHistoryTransformer;

    @SaveOperation
    @GetMapping("/findAll")
    @ApiOperation(value = "Retrieve the list of calls to application endpoints", response = TransactionHistoryDTO.class)
    public TransactionHistoryDTO findAll(@RequestParam(defaultValue = FIRST_PAGE) Integer page,
                                         @RequestParam(name = PAGE_SIZE, defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize) {
        TransactionHistory transactionHistory = this.transactionHistoryService.getTransactionHistory(new TransactionHistoryRequest(page, pageSize));
        return this.transactionHistoryTransformer.transform(transactionHistory);
    }

}

