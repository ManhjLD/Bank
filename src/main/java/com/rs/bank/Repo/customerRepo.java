package com.rs.bank.Repo;

import com.rs.bank.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  customerRepo extends CrudRepository<Customer, Integer> {
    List<Customer> findByUsername(String username);
}
