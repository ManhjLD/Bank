package com.rs.bank.Repo;

import com.rs.bank.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface TransactionRepo extends CrudRepository<Transaction, Integer> {
    List<Transaction> findByTime(LocalDateTime time);
    List<Transaction> findByFromAccount(String fromAccount);
    List<Transaction> findByToAccount(String toAccount);
}