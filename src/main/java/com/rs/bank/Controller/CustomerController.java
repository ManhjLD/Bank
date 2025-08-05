package com.rs.bank.Controller;

import com.rs.bank.model.Customer;
import com.rs.bank.service.CustomUserDetails;
import com.rs.bank.service.CustomerService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Customer")
public class CustomerController {


    @Autowired
    private CustomerService customerservice;

    @GetMapping("/get-info")
    public ResponseEntity<String> getCustomerInfo(@RequestParam String username){
        ResponseEntity<String> response = null;
        try {
            Customer customer = customerservice.findCustomerByUsername(username);
            if (customer != null) {
                response = ResponseEntity.status(HttpStatus.OK)
                        .body(
                                "User name: " + customer.getName() + "\n"
                                        +"User email: " + customer.getEmail() + "\n"
                                        +"User phone number: " + customer.getUsername() + "\n"
                                        +"User Address: " + customer.getAddress()
                        );
            }
        }
        catch (Exception exception) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An Exception Occurred: " + exception);
        }
        return response;
    }
}
