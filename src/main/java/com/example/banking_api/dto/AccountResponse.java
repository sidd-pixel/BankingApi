package com.example.banking_api.dto;

import java.math.BigDecimal;

public class AccountResponse {
    private Long id;
    private String name;
    private BigDecimal balance;
    private Long customerId;


    public AccountResponse(){

    }


    public AccountResponse(Long id, String name, BigDecimal balance, Long customerId) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.customerId = customerId;
    }


    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public BigDecimal getBalance() {
        return balance;
    }


    public Long getCustomerId() {
        return customerId;
    }
    

   
}
