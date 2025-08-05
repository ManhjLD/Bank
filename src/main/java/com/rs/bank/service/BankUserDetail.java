package com.rs.bank.service;

import com.rs.bank.Repo.customerRepo;
import com.rs.bank.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class BankUserDetail implements UserDetailsService {

    @Autowired
    private customerRepo customer_repo;

    @Override
    public UserDetails loadUserByUsername(String  username) throws UsernameNotFoundException{
        String password = null;
        List<Customer> customers = customer_repo.findByUsername(username);
        List<GrantedAuthority> authorities = null;
        if(customers.isEmpty()){
            throw new UsernameNotFoundException("Phone number: "+username+" not found");
        }
        username = customers.get(0).getUsername();
        password = customers.get(0).getPassword();
        authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(customers.get(0).getRole()));
        return new User(username, password, authorities);
    }
}
