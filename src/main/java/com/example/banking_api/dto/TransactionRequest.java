package com.example.banking_api.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class TransactionRequest {
    @NotNull (message = "amount is required")
    @Positive( message= "Amount must be greater than 0")
    private BigDecimal amount;

    public TransactionRequest(){}

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
    