package com.rs.bank.service;

import com.rs.bank.Repo.CustomerRepo;
import com.rs.bank.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private CustomerRepo customer_repo;

    public Customer getCurrentCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        String username = authentication.getName();
        Optional<Customer> optionalCustomer = customer_repo.findByUsername(username).stream().findFirst();

        if (optionalCustomer.isPresent()) {
            return optionalCustomer.get();
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
