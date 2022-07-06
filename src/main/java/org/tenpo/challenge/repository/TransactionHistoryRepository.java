package org.tenpo.challenge.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.tenpo.challenge.model.dao.OperationDAO;

@Repository
public interface TransactionHistoryRepository extends PagingAndSortingRepository<OperationDAO, Long> {

}
