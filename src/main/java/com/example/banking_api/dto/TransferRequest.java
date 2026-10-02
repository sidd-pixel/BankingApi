package com.example.banking_api.dto;

import java.math.BigDecimal;

//import javax.print.attribute.standard.Destination;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class TransferRequest {

    @NotNull (message = "source account id is required")
    private Long sourceAccountId;

    @NotNull (message = "Destination account id is required")
    private Long destinationAccountId;

    @NotNull (message="Amount is required")
    @Positive (message = "transfer amount must be greater than 0")
    private BigDecimal amount;


    public TransferRequest(){}


    public Long getSourceAccountId() {
        return sourceAccountId;
    }


    public void setSourceAccountId(Long sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }


    public Long getDestinationAccountId() {
        return destinationAccountId;
    }


    public void setDestinationAccountId(Long destinationAccountId) {
        this.destinationAccountId = destinationAccountId;
    }


    public BigDecimal getAmount() {
        return amount;
    }


    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    
}
