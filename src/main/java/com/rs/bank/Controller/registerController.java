package com.rs.bank.Controller;

import com.rs.bank.model.Customer;
import com.rs.bank.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Service")
public class registerController {
    @Autowired
    CustomerService customerservice;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
        ResponseEntity<String> response = null;
        if (customerservice.findCustomerByUsername(customer.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Phone number is already used");
        }
        try {
            Customer SavedCustomer = customerservice.createCustomer(customer);
            if (SavedCustomer.getId() > 0) {
                response = ResponseEntity.status(HttpStatus.CREATED)
                        .body("Customer with id " + SavedCustomer.getId() + " has been registered successfully");
            }


        } catch (Exception exception) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An Exception Occurred: " + exception);
        }
        return response;
    }
}
