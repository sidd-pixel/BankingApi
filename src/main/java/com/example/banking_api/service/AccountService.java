package com.example.banking_api.service;

import java.util.List;



import org.springframework.stereotype.Service;

import com.example.banking_api.dto.AccountMapper;
import com.example.banking_api.dto.AccountRequest;
import com.example.banking_api.dto.AccountResponse;
import com.example.banking_api.dto.TransactionMapper;
import com.example.banking_api.dto.TransactionRequest;
import com.example.banking_api.dto.TransactionResponse;
import com.example.banking_api.dto.TransferRequest;
import com.example.banking_api.exception.AccountNotFoundException;
import com.example.banking_api.exception.InsufficientBalanceException;
import com.example.banking_api.model.Account;
import com.example.banking_api.model.Customer;
import com.example.banking_api.model.Transaction;
import com.example.banking_api.repository.AccountRepository;
import com.example.banking_api.repository.CustomerRepository;
import com.example.banking_api.repository.TransactionRepository;

import jakarta.transaction.Transactional;

@Service 
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository,CustomerRepository customerRepository,TransactionRepository transactionRepository){
        this.accountRepository=accountRepository;
        this.customerRepository=customerRepository;
        this.transactionRepository=transactionRepository;
    }

    public AccountResponse createAccount(AccountRequest request){
        Customer customer=customerRepository
                    .findById(request.getCustomerId())
                    .orElseThrow(()->new AccountNotFoundException("Customer id not found "+request.getCustomerId()));

        Account account=new Account();
        account.setName(request.getName());
        account.setBalance(request.getBalance());
        account.setCustomer(customer);

        Account savedAccount= accountRepository.save(account);

        return AccountMapper.toResponse(savedAccount);
    }

    public List<Account> getAccounts(){
        return accountRepository.findAll();
    }
    
    
    public Account getAccountById(Long id) {
    return accountRepository
            .findById(id)
            .orElseThrow(() ->
                new AccountNotFoundException("Account not found with id: " + id)
            );
    }

    public Account updateAccount(Long id, Account updatedAccount){
        Account account=accountRepository
                .findById(id)
                .orElseThrow(()->new AccountNotFoundException("Account not found with id: "+id));
        account.setName(updatedAccount.getName());
        account.setBalance(updatedAccount.getBalance());

        return accountRepository.save(account);

    }

    public void deleteAccount(Long id){
        if(!accountRepository.existsById(id)){
            throw new AccountNotFoundException("Account not found with id: "+id);
        }
        accountRepository.deleteById(id);
    }

    @Transactional 
    public void transferMonery(TransferRequest request){
        Account sourceAccount=accountRepository.findById(request.getSourceAccountId()
        ).orElseThrow(()->new AccountNotFoundException(
            "Source account not found"
        ));


        Account destinationAccount = accountRepository.findById(
                request.getDestinationAccountId()
        ).orElseThrow(() -> new AccountNotFoundException(
                "Destination account not found"
        ));

        if(sourceAccount.getId().equals(destinationAccount.getId())){
            throw new IllegalArgumentException("source and the destination cannot have the same id");
        }

        if (sourceAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }
        sourceAccount.setBalance(
            sourceAccount.getBalance().subtract(request.getAmount())
        );

        destinationAccount.setBalance(
            destinationAccount.getBalance().add(request.getAmount())
        );

        Transaction outGoingTransaction=new Transaction();
        outGoingTransaction.setAmount(request.getAmount());
        outGoingTransaction.setType("TRANSFER_OUT");
        outGoingTransaction.setAccount(sourceAccount);
        outGoingTransaction.setRelatedAccount(destinationAccount);

        Transaction incomingTransaction=new Transaction();
        incomingTransaction.setAmount(request.getAmount());
        incomingTransaction.setType("TRANSFER_IN");
        incomingTransaction.setAccount(destinationAccount); 
        incomingTransaction.setRelatedAccount(sourceAccount);

        transactionRepository.save(outGoingTransaction);
        transactionRepository.save(incomingTransaction);
    }

    @Transactional 
    public void deposit(Long accountId,TransactionRequest request){
        Account savedAccount=accountRepository.findById(accountId).orElseThrow(()->new AccountNotFoundException("Account not available"));

        savedAccount.setBalance(
            savedAccount.getBalance().add(request.getAmount())
        );  

        Transaction transaction=new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setType("DEPOSIT");
        transaction.setAccount(savedAccount);

        transactionRepository.save(transaction);
    }   

    @Transactional 
    public void withdraw(Long accountId,TransactionRequest request){
        Account savedAccount=accountRepository.findById(accountId).orElseThrow(()->new AccountNotFoundException("Account with this id not found"));

        if(savedAccount.getBalance().compareTo(request.getAmount())<0){
            throw new InsufficientBalanceException("Insufficient balance");
        }

        savedAccount.setBalance(savedAccount.getBalance().subtract(request.getAmount()));

        Transaction transaction=new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setType("WITHDRAW");
        transaction.setAccount(savedAccount);

        transactionRepository.save(transaction);
    }

    public List<TransactionResponse>getTransactions(Long accountId){
        if(!accountRepository.existsById(accountId)){
            throw new AccountNotFoundException("Account not found");
        }
        List<Transaction> transactions=transactionRepository.findByAccountId(accountId);

        return transactions.stream()
                .map(TransactionMapper::toResponse)
                .toList();
    }

}
