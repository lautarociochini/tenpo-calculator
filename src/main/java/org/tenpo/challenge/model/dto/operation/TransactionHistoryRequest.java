package org.tenpo.challenge.model.dto.operation;

public class TransactionHistoryRequest {

    private final int page;
    private final int pageSize;

    public TransactionHistoryRequest(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }
}
