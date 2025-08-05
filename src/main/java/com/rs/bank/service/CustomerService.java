package com.rs.bank.service;

import com.rs.bank.Repo.customerRepo;
import com.rs.bank.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    customerRepo customerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Customer createCustomer(Customer customer){
        String password = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(password);
        return customerRepository.save(customer);
    }

    public Customer findCustomerByUsername(String username){
        List<Customer> customers = customerRepository.findByUsername(username);
        for(Customer c : customers){
            if(c.getUsername().equals(username)){
                return c;
            }
        }
        return null;
    }
    public Customer updateCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Customer customer){
        customerRepository.delete(customer);
    }
}
