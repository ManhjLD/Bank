package com.rs.bank.Controller;

import com.rs.bank.model.Account;
import com.rs.bank.service.AccountService;
import com.rs.bank.service.UserService;
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

    @Autowired
    private UserService userService;

    @PostMapping("/new")
    public ResponseEntity<String> newAccount(@RequestParam String number) {
        String owner = userService.getCurrentCustomer().getUsername();

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
            account.setOwner(owner);
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
        String owner = userService.getCurrentCustomer().getUsername();

        ResponseEntity<String> response = null;
        Account account = accountService.GetAccountByNumber(number);
        if(account != null && account.getOwner().equals(owner)){
            response = ResponseEntity.status(HttpStatus.OK)
                    .body(account.getBalance().toString());
        }
        else {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("You don't have account with number :" + number);
        }
        return response;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAccountByNumber(@RequestParam String number) {
        String owner = userService.getCurrentCustomer().getUsername();

        ResponseEntity<String> response = null;
        Account account = accountService.GetAccountByNumber(number);
        if (account != null && account.getOwner().equals(owner)) {
            accountService.DeleteAccount(account);
            response = ResponseEntity.status(HttpStatus.OK)
                    .body("Customer with number " + number + " has been deleted successfully");
        } else {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("You don't have account with number :" + number);
        }
        return response;
    }
}
