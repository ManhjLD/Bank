package com.rs.bank.Repo;

import com.rs.bank.model.Account;
import com.rs.bank.model.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepo extends CrudRepository<Account, Integer> {
    List<Account> findByAccountNumber(String accountNumber);
   List<Account> findByOwner(Customer customer);
}
