package com.rs.bank.service;

import com.rs.bank.Repo.customerRepo;
import com.rs.bank.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private customerRepo customerRepo;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Customer customer = customerRepo.findByUsername(username).get(0);
            if(customer == null){
                throw new UsernameNotFoundException("Phone number not found");
            }
            return new CustomUserDetails(customer);
        }
}

