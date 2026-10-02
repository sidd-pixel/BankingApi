package com.example.banking_api.service;

import org.springframework.stereotype.Service;

import com.example.banking_api.model.Customer;
import com.example.banking_api.repository.CustomerRepository;

@Service 
public class CustomerService {
    
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository=customerRepository;
    }

    public Customer createCustomer(Customer customer){
        return customerRepository.save(customer);
    }
}
