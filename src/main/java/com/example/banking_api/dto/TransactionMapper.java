package com.example.banking_api.dto;

import com.example.banking_api.model.Transaction;

public class TransactionMapper {
    public static TransactionResponse toResponse(Transaction transaction){
        return new TransactionResponse(
            transaction.getId(), 
            transaction.getAmount(), 
            transaction.getType(), 
            transaction.getRelatedAccount() != null
                ? transaction.getRelatedAccount().getId()
                : null);
    }
}
