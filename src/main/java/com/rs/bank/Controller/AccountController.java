package com.rs.bank.Controller;

import com.rs.bank.model.Account;
import com.rs.bank.model.Customer;
import com.rs.bank.service.AccountService;
import com.rs.bank.service.CurrentCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/Account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/new")
    public ResponseEntity<String> newAccount(@RequestParam String number) {

        ResponseEntity<String> response = null;
        if(accountService.GetAccountByNumber(number) != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Account number is already used");
        }
        try {
            Account account = new Account();
            account.setAccountNumber(number);
            account.setBalance(0);
            account.setCreatedAt(LocalDateTime.now());
            account.setOwner(CurrentCustomer.getCurrentCustomer());
            Account newAccount = accountService.CreateAccount(account);
            if (newAccount.getAccountNumber() != null) {
                response = ResponseEntity.status(HttpStatus.CREATED)
                        .body("Customer with number " + newAccount.getAccountNumber() + " has been registered successfully");
            }


        } catch (Exception exception) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An Exception Occurred: " + exception);
        }
        return response;
    }
    @GetMapping("/get-balance")
    public ResponseEntity<String> getAccount(@RequestParam String number) {
        ResponseEntity<String> response = null;
        Customer currentCustomer = CurrentCustomer.getCurrentCustomer();
        Account account = accountService.GetAccountByNumber(number);
        if(!account.getOwner().equals(currentCustomer)){
            response = ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Account number " + number + "not found");
        }
        if(account != null){
            response = ResponseEntity.status(HttpStatus.OK)
                    .body(account.getBalance().toString());
        }
        return response;
    }
    @PatchMapping("/update")
    public ResponseEntity<String> updateAccount(@RequestParam String number, @RequestParam String balance) {
        ResponseEntity<String> response = null;
        Account account = accountService.GetAccountByNumber(number);
        Customer currentCustomer = CurrentCustomer.getCurrentCustomer();
        if(!account.getOwner().equals(currentCustomer)){
            response = ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Account number " + number + "not found");
        }
        account.setBalance(Integer.parseInt(balance));
        try {
            accountService.UpdateAccount(account);
            if(accountService.GetAccountByNumber(number) != null){
                response = ResponseEntity.status(HttpStatus.OK)
                .body("Customer with number " + number+ " has been updated successfully");
            }
        }
        catch (Exception exception) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An Exception Occurred: " + exception);
        }
        return response;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAccountByNumber(@RequestParam String number) {
        ResponseEntity<String> response = null;
        Account deleteAccount = accountService.GetAccountByNumber(number);
        try {
            accountService.DeleteAccount(deleteAccount);
            if(accountService.GetAccountByNumber(number) == null){
                response = ResponseEntity.status(HttpStatus.OK)
                        .body("Customer with number " + number + " has been deleted successfully");
            }
        }
        catch (Exception exception) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An Exception Occurred: " + exception);
        }
        return response;
    }
}
