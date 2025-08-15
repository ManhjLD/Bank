package com.rs.bank.service;

import com.rs.bank.Repo.AccountRepo;
import com.rs.bank.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    private  AccountRepo accountRepo;

    public Account CreateAccount(Account account){
        Account newAccount = accountRepo.save(account);
        return newAccount;
    }

    public Account GetAccountByNumber(String account_number){
        if(accountRepo.findByAccountNumber(account_number).isEmpty() ) {
            return null;
        }
        else {
            Account account = accountRepo.findByAccountNumber(account_number).get(0);
            return account;
        }
    }
    public List<Account> GetAccountByOwner(String owner){
        return  accountRepo.findByOwner(owner);
    }

    public Account UpdateAccount(Account account){
        return accountRepo.save(account);
    }
    public void DeleteAccount(Account account){
        accountRepo.delete(account);
    }
}
