package com.example.banking_api.dto;

import java.math.BigDecimal;

public class TransactionResponse {
    private Long id;
    private BigDecimal amount;
    private String type;
    private Long relatedAccountId;

    TransactionResponse(Long id,BigDecimal amount,String type ,Long relatedAccountId){
        this.id=id;
        this.amount=amount;
        this.type=type;
        this.relatedAccountId=relatedAccountId;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public Long getRelatedAccountId() {
        return relatedAccountId;
    }

    
}
