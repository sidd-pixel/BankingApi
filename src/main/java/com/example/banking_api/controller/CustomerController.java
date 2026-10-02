package com.example.banking_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.banking_api.model.Customer;
import com.example.banking_api.service.CustomerService;

@RestController 
@RequestMapping ("/customers")
public class CustomerController {
    private final CustomerService customerService;
    public CustomerController(CustomerService customerService){
        this.customerService=customerService;
    }

    @PostMapping 
    public ResponseEntity<Customer>createCustomer(@RequestBody Customer customer){

        Customer savedCustomer=customerService.createCustomer(customer);

        return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedCustomer);
    }
}
