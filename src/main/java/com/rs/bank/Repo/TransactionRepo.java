package com.rs.bank.Repo;

import com.rs.bank.model.Account;
import com.rs.bank.model.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepo extends CrudRepository<Transaction, Integer> {
    List<Transaction> findByTimestamp(LocalDateTime timestamp);
}
