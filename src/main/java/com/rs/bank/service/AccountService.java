package com.rs.bank.service;

import com.rs.bank.Repo.AccountRepo;
import com.rs.bank.model.Account;
import com.rs.bank.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {
    @Autowired
    private  AccountRepo accountRepo;

    public Account CreateAccount(Account account){
        Account newAccount = accountRepo.save(account);
        return newAccount;
    }

    public Account GetAccountByNumber(String account){
        Customer currentUser = CurrentCustomer.getCurrentCustomer();
        List<Account> accounts1 = accountRepo.findByOwner(currentUser);
        List<Account> accounts = accounts1.stream().filter(a -> a.getAccountNumber().equals(account)).collect(Collectors.toList());
        if(accounts.isEmpty()){
            return null;
        }
        else
            return accounts.get(0);
    }
    public Account UpdateAccount(Account account){
        return accountRepo.save(account);
    }
    public void DeleteAccount(Account account){
        accountRepo.delete(account);
    }
}
