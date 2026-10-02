package com.example.banking_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.banking_api.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction,Long>{

    List<Transaction>findByAccountId(Long accountId);
}
