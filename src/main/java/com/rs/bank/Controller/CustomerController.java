package com.rs.bank.Controller;

import com.rs.bank.model.Customer;
import com.rs.bank.service.CustomerService;
import com.rs.bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Customer")
public class CustomerController {


    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/get-info")
    public ResponseEntity<String> getCustomerInfo(@RequestParam String username){
        ResponseEntity<String> response = null;
        try {
            Customer customer = userService.getCurrentCustomer();
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
    @PatchMapping("/update")
    public ResponseEntity<String> updateCustomer(@RequestParam String name,
                                                 @RequestParam String email,
                                                 @RequestParam String birthday,
                                                 @RequestParam String address
    )
    {
        ResponseEntity<String> response = null;
        try {
            Customer user = userService.getCurrentCustomer();
            user.setName(name);
            user.setEmail(email);
            user.setBirthday(birthday);
            user.setAddress(address);
            customerService.updateCustomer(user);
        }
        catch (Exception exception) {
            response = ResponseEntity.status(HttpStatus.OK)
                    .body("An Exception Occurred: " + exception);
        }
        return response;
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCustomer(){
        ResponseEntity<String> response = null;
        Customer user = userService.getCurrentCustomer();
        try {
            customerService.deleteCustomer(user);
            response = ResponseEntity.status(HttpStatus.OK)
                    .body("Customer deleted successfully");
        }
        catch (Exception exception) {
            response = ResponseEntity.status(HttpStatus.OK)
                    .body("An Exception Occurred: " + exception);
        }
        return response;
    }
}
