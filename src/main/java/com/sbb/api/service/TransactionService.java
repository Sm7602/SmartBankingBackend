package com.sbb.api.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sbb.api.dao.AccountRepository;
import com.sbb.api.dao.TransactionRepository;
import com.sbb.api.entity.Account;
import com.sbb.api.entity.Transaction;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Transaction deposit(String accountNumber,BigDecimal amount,String remarks) {
        System.out.println("TransactionService.deposit()");
        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() ->
                        new RuntimeException("Account not found"));
       
        if(!account.getActive()) {
            throw new RuntimeException("Account is inactive");
        }
        
        if(amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Invalid amount");
        }

        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);

        Transaction transaction = new Transaction();

        transaction.setTransactionReference(UUID.randomUUID().toString());
        transaction.setTransactionType("DEPOSIT");
        transaction.setAmount(amount);
        transaction.setAvailableBalance(account.getBalance());
        transaction.setRemarks(remarks);
        transaction.setStatus("SUCCESS");
        transaction.setTransactionTime(LocalDateTime.now());
        transaction.setAccount(account);

        return transactionRepository.save(transaction);
    }

    public Transaction withdraw(String accountNumber,BigDecimal amount,String remarks) {
        System.out.println("TransactionService.withdraw()");
        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() ->
                        new RuntimeException("Account not found"));
        
        if(!account.getActive()) {
            throw new RuntimeException("Account is inactive");
        }
        
        if(amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Invalid amount");
        }
        
        if(account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }
        
        if(amount.compareTo(account.getWithdrawalLimit()) > 0) {
            throw new RuntimeException("Withdrawal limit exceeded");
        }
        
        BigDecimal remainingBalance =account.getBalance().subtract(amount);

        if(remainingBalance.compareTo(account.getMinimumBalance()) < 0) {
            throw new RuntimeException("Minimum balance must be maintained");
        }
        

        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
        
        Transaction transaction = new Transaction();
        transaction.setTransactionReference(UUID.randomUUID().toString());
        transaction.setTransactionType("WITHDRAW");
        transaction.setAmount(amount);
        transaction.setAvailableBalance(account.getBalance());
        transaction.setRemarks(remarks);
        transaction.setStatus("SUCCESS");
        transaction.setTransactionTime(LocalDateTime.now());
        transaction.setAccount(account);

        return transactionRepository.save(transaction);
    }
    
    public Transaction transfer(String fromAccountNumber, String toAccountNumber,BigDecimal amount,String remarks) {
        System.out.println("TransactionService.transfer()");
        Account sender = accountRepository.findByAccountNumber(fromAccountNumber) .orElseThrow(() ->
                        new RuntimeException("Sender account not found"));

        Account receiver = accountRepository.findByAccountNumber(toAccountNumber).orElseThrow(() ->
                        new RuntimeException("Receiver account not found"));

        if(!sender.getActive()) {
            throw new RuntimeException("Sender account inactive");
        }

        if(!receiver.getActive()) {
            throw new RuntimeException("Receiver account inactive");
        }
        
        if(fromAccountNumber.equals(toAccountNumber)) {
            throw new RuntimeException("Cannot transfer to same account");
        }
        
        if(sender.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }
        
        if(amount.compareTo(sender.getDailyTransferLimit()) > 0) {
            throw new RuntimeException("Transfer limit exceeded");
        }
        
        BigDecimal remainingBalance =sender.getBalance().subtract(amount);

        if(remainingBalance.compareTo(sender.getMinimumBalance()) < 0) {
            throw new RuntimeException("Minimum balance must be maintained");
        }

        sender.setBalance(sender.getBalance().subtract(amount));

        receiver.setBalance(receiver.getBalance().add(amount));

        accountRepository.save(sender);
        accountRepository.save(receiver);

        Transaction transaction = new Transaction();

        transaction.setTransactionReference(UUID.randomUUID().toString());
        transaction.setTransactionType("TRANSFER");
        transaction.setAmount(amount);
        transaction.setAvailableBalance(sender.getBalance());
        transaction.setRemarks(remarks);
        transaction.setStatus("SUCCESS");
        transaction.setTransactionTime(LocalDateTime.now());
        transaction.setAccount(sender);

        return transactionRepository.save(transaction);
    }

    public Transaction getTransactionById(Long id) {
        System.out.println("TransactionService.getTransactionById()");
        return transactionRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Transaction not found"));
    }

    public List<Transaction> getAllTransactions() {
        System.out.println("TransactionService.getAllTransactions()");
        return transactionRepository.findAll();
    }

    public List<Transaction> getTransactionsByAccount(String accountNumber) {
        System.out.println("TransactionService.getTransactionsByAccount()");
        return transactionRepository.findByAccountAccountNumber(accountNumber);
    }

    public void deleteTransaction(Long id) {
        System.out.println("TransactionService.deleteTransaction()");
        Transaction transaction =getTransactionById(id);
        transactionRepository.delete(transaction);
    }
}
