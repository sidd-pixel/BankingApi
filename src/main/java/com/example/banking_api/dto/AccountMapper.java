package com.example.banking_api.dto;

import com.example.banking_api.model.Account;

public class AccountMapper {
    public static AccountResponse toResponse(Account account){
        return new AccountResponse(
            account.getId(),
            account.getName(),
            account.getBalance(),
            account.getCustomer().getId()
        );
    }
}
