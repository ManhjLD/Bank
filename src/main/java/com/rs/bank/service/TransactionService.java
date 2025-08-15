package com.rs.bank.service;

import com.rs.bank.Repo.TransactionRepo;
import com.rs.bank.model.Account;
import com.rs.bank.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private AccountService accountService;

    public Transaction CreateTransaction(
            Transaction transaction
    )
    {
        Account fromAccount = accountService.GetAccountByNumber(transaction.getFromAccount());
        Account toAccount = accountService.GetAccountByNumber(transaction.getToAccount());
        fromAccount.setBalance(fromAccount.getBalance() - (transaction.getAmount()));
        toAccount.setBalance(toAccount.getBalance() + (transaction.getAmount()));
        accountService.UpdateAccount(fromAccount);
        accountService.UpdateAccount(toAccount);
        return transactionRepo.save(transaction);
    }
    public List<Transaction> GetTransactionByToAccount(String toNumber)
    {
        return transactionRepo.findByToAccount(toNumber);
    }
    public List<Transaction> GetTransactionByFromAccount(String fromNumber)
    {
        return transactionRepo.findByFromAccount(fromNumber);
    }
    public  List<Transaction> GetTransactionByDate(LocalDateTime date)
    {
        return transactionRepo.findByTime(date);
    }
}
