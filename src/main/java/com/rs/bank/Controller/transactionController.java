package com.rs.bank.Controller;

import com.rs.bank.model.Account;
import com.rs.bank.model.Transaction;
import com.rs.bank.service.AccountService;
import com.rs.bank.service.TransactionService;
import com.rs.bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/Transaction")
public class TransactionController {
    @Autowired
    TransactionService  transactionService;
    @Autowired
    UserService userService;
    @Autowired
    AccountService accountService;

    @PostMapping("/new")
    public ResponseEntity<String> newTransaction(@RequestParam String from,@RequestParam String to, @RequestParam Integer amount,@RequestParam String description) {
        ResponseEntity<String> response = null;
        String owner = userService.getCurrentCustomer().getUsername();
        Account account = accountService.GetAccountByNumber(from);
        if(account == null) return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account not found");
        if(!account.getOwner().equals(owner)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You do not have account with number :" + from);

        Account toAccount = accountService.GetAccountByNumber(to);
        if(toAccount == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account with number :" + to + " not found");

        if(account.getBalance().compareTo(toAccount.getBalance()) < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You don't have enough balance to transfer this transaction");
        }

        Transaction transaction = new Transaction();
        transaction.setFromAccount(from);
        transaction.setToAccount(to);
        transaction.setTime(LocalDateTime.now());
        transaction.setAmount(amount);
        transaction.setType("TRANSFER");
        transaction.setDescription(description);
        try {
            transactionService.CreateTransaction(transaction);
            response = ResponseEntity.status(HttpStatus.OK).body("Transaction successful");
        }
        catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An Exception Occurred: " + e);
        }
        return response;
    }
    @GetMapping("/search")
    public ResponseEntity<String> searchTransaction(@RequestParam String keyword) {
        ResponseEntity<String> response = null;
        String owner = userService.getCurrentCustomer().getUsername();
        try {
            String result = "";
            List<Account> ownerAccount = accountService.GetAccountByOwner(owner);
            for(Account account : ownerAccount) {
                List<Transaction> transactions = transactionService.GetTransactionByFromAccount(account.getAccountNumber());
                for (Transaction transaction : transactions) {
                    if(transaction.getToAccount().equals(keyword)||transaction.getFromAccount().equals(keyword)) {
                        result = result + "From: " + transaction.getFromAccount() + " To: "+transaction.getToAccount() + " Amount: " + transaction.getAmount() + " Description: " + transaction.getDescription()+"\r\n";
                    }
                }
            }
            if(result == "") result = "No transactions found";
            response = ResponseEntity.status(HttpStatus.OK).body(result);
        }
        catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An Exception Occurred: " + e);
        }
        return response;
    }
}
