package com.example.banking_api.controller;

import java.util.List;
//import java.util.Optional;

//import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.banking_api.dto.AccountRequest;
import com.example.banking_api.dto.AccountResponse;
import com.example.banking_api.dto.TransactionRequest;
import com.example.banking_api.dto.TransactionResponse;
import com.example.banking_api.dto.TransferRequest;
import com.example.banking_api.model.Account;
import com.example.banking_api.service.AccountService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping ("/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService){
        this.accountService=accountService;
    }

    @PostMapping 
    public ResponseEntity<AccountResponse>createAccount(@Valid @RequestBody AccountRequest request){
        AccountResponse response=accountService.createAccount(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping 
    public List<Account>getAccounts(){
        return accountService.getAccounts();
    }

    @GetMapping ("/{id}")
    public Account getAccountById(@PathVariable Long id){
        return accountService.getAccountById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account>updateAccount(@PathVariable Long id,@RequestBody Account updatedAccount){

        Account savedAccount=accountService.updateAccount(id, updatedAccount);

        return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedAccount);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void>deleteAccount(@PathVariable Long id){
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping ("/transfer")
    public ResponseEntity<Void>transferMoney(@Valid @RequestBody TransferRequest request){
        accountService.transferMonery(request);

        return ResponseEntity.noContent().build();  
    }

    @PostMapping ("/{id}/deposit")
    public ResponseEntity<Void>deposit(@PathVariable Long id,@Valid @RequestBody TransactionRequest request){
        accountService.deposit(id, request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping ("/{id}/withdraw")
    public ResponseEntity<Void>withdraw(@PathVariable Long id,@Valid @RequestBody TransactionRequest request){
        accountService.withdraw(id, request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping ("/{id}/transactions")
    public List<TransactionResponse>getTransactions(@PathVariable Long id){
        return accountService.getTransactions(id);
    }

}
