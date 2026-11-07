package com.billing.system.controller;

import com.billing.system.model.Customer;
import com.billing.system.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer){
        try {
            return ResponseEntity.accepted().body(customerService.save(customer));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("error: " + e.getMessage());
        }
    }

}
