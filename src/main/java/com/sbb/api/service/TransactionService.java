package com.sbb.api.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sbb.api.dao.AccountRepository;
import com.sbb.api.dao.TransactionRepository;
import com.sbb.api.dto.transaction.TransactionRequest;
import com.sbb.api.dto.transaction.TransactionResponse;
import com.sbb.api.dto.transaction.TransferRequest;
import com.sbb.api.entity.Account;
import com.sbb.api.entity.Transaction;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;
    
    private TransactionResponse convertToResponse(Transaction transaction) {

        return TransactionResponse.builder()
                .id(transaction.getId())
                .transactionReference(transaction.getTransactionReference())
                .transactionType(transaction.getTransactionType())
                .amount(transaction.getAmount())
                .availableBalance(transaction.getAvailableBalance())
                .remarks(transaction.getRemarks())
                .status(transaction.getStatus())
                .transactionTime(transaction.getTransactionTime())
                .account(transaction.getAccount())
                .build();
    }

    public TransactionResponse deposit(TransactionRequest request) {
        System.out.println("TransactionService.deposit()");
        
        Account account = accountRepository.findByAccountNumber(request.getAccountNumber()).orElseThrow(() ->
                        new RuntimeException("Account not found"));
        
        BigDecimal currentBalance = account.getBalance();
       
        if(!account.getActive()) {
            throw new RuntimeException("Account is inactive");
        }
        
        if(request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Invalid amount");
        }

        BigDecimal newBalance =  currentBalance.add(request.getAmount());
        account.setBalance(newBalance);
       
        accountRepository.save(account);

        Transaction transaction =Transaction.builder()
        		    .transactionReference(UUID.randomUUID().toString())
                .transactionType(request.getTransactionType())
                .amount(request.getAmount())
                .availableBalance(newBalance)
                .remarks(request.getRemarks())
                .status("SUCCESS")
                .transactionTime(LocalDateTime.now())
                .account(account)
                .build();

        transaction= transactionRepository.save(transaction);
        return convertToResponse(transaction);
    }

    public TransactionResponse withdraw(TransactionRequest request) {
        System.out.println("TransactionService.withdraw()");
        Account account = accountRepository.findByAccountNumber(request.getAccountNumber()).orElseThrow(() ->
                        new RuntimeException("Account not found"));
       
        BigDecimal currentBalance = account.getBalance();
        
        if(!account.getActive()) {
            throw new RuntimeException("Account is inactive");
        }
        
        if(request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Invalid amount");
        }
        
        if(account.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance");
        }
        
        LocalDate today = LocalDate.now();

        LocalDateTime startOfDay =today.atStartOfDay();

        LocalDateTime endOfDay =today.plusDays(1).atStartOfDay();

        
        BigDecimal dailyWithdraw = transactionRepository.getTodayWithdrawAmount(account.getId(),startOfDay,endOfDay);

        if (dailyWithdraw == null) {
        	dailyWithdraw = BigDecimal.ZERO;
        }

        BigDecimal withdrawalLimit =account.getWithdrawalLimit();

        if (withdrawalLimit != null &&
        		dailyWithdraw.add(request.getAmount()).compareTo(withdrawalLimit) > 0) {

            throw new RuntimeException("Daily transfer limit exceeded");
        }
        
        
        BigDecimal remainingBalance =currentBalance.subtract(request.getAmount());

        if(remainingBalance.compareTo(account.getMinimumBalance()) < 0) {
            throw new RuntimeException("Minimum balance must be maintained");
        }
        

        BigDecimal newBalance =  currentBalance.subtract(request.getAmount());
        accountRepository.save(account);
        
        Transaction transaction =Transaction.builder()
    		    .transactionReference(UUID.randomUUID().toString())
            .transactionType(request.getTransactionType())
            .amount(request.getAmount())
            .availableBalance(newBalance)
            .remarks(request.getRemarks())
            .status("SUCCESS")
            .transactionTime(LocalDateTime.now())
            .account(account)
            .build();

    transaction= transactionRepository.save(transaction);
    return convertToResponse(transaction);
        
    }
    
    public TransactionResponse transfer(TransferRequest request) {
        System.out.println("TransactionService.transfer()");
        Account sender = accountRepository.findByAccountNumber(request.getFromAccountNumber()) .orElseThrow(() ->
                        new RuntimeException("Sender account not found"));

        Account receiver = accountRepository.findByAccountNumber(request.getToAccountNumber()).orElseThrow(() ->
                        new RuntimeException("Receiver account not found"));
        
        if (request.getFromAccountNumber() == null ||
                request.getFromAccountNumber().isBlank()) {

            throw new IllegalArgumentException("Sender account number is required");
        }

        if (request.getToAccountNumber() == null ||
                request.getToAccountNumber().isBlank()) {

            throw new IllegalArgumentException(
                    "Receiver account number is required");
        }

        if (request.getAmount() == null ||
                request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "Transfer amount must be greater than zero");
        }

        if(!sender.getActive()) {
            throw new RuntimeException("Sender account inactive");
        }

        if(!receiver.getActive()) {
            throw new RuntimeException("Receiver account inactive");
        }
        
        if(request.getFromAccountNumber().equals(request.getToAccountNumber())) {
            throw new RuntimeException("Cannot transfer to same account");
        }
        
        if(sender.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance");
        }
        
        LocalDate today = LocalDate.now();

        LocalDateTime startOfDay =today.atStartOfDay();

        LocalDateTime endOfDay =today.plusDays(1).atStartOfDay();
        
        BigDecimal dailyTransferred = transactionRepository.getTodayTransferAmount(sender.getId(),startOfDay,endOfDay);

        if (dailyTransferred == null) {
            dailyTransferred = BigDecimal.ZERO;
        }

        BigDecimal dailyLimit =sender.getDailyTransferLimit();

        if (dailyLimit != null &&
                dailyTransferred.add(request.getAmount()).compareTo(dailyLimit) > 0) {

            throw new RuntimeException("Daily transfer limit exceeded");
        }
        
        BigDecimal remainingBalance =sender.getBalance().subtract(request.getAmount());

        if(remainingBalance.compareTo(sender.getMinimumBalance()) < 0) {
            throw new RuntimeException("Minimum balance must be maintained");
        }

        sender.setBalance(sender.getBalance().subtract(request.getAmount()));

        receiver.setBalance(receiver.getBalance().add(request.getAmount()));

        accountRepository.save(sender);
        accountRepository.save(receiver);
        
        Transaction transaction =Transaction.builder()
    		    .transactionReference(UUID.randomUUID().toString())
            .transactionType(request.getTransactionType())
            .amount(request.getAmount())
            .availableBalance(sender.getBalance())
            .remarks(request.getRemarks())
            .status("SUCCESS")
            .transactionTime(LocalDateTime.now())
            .account(sender)
            .build();

    transaction= transactionRepository.save(transaction);
    return convertToResponse(transaction);

    }

    public TransactionResponse getTransactionById(Long id) {
        System.out.println("TransactionService.getTransactionById()");
        Transaction transaction= transactionRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Transaction not found"));
        return convertToResponse(transaction);
    }

    public List<TransactionResponse> getAllTransactions() {
        System.out.println("TransactionService.getAllTransactions()");
        return transactionRepository.findAll()
        		    .stream()
	            .map(this::convertToResponse)
	            .toList();
    }

    public List<TransactionResponse> getTransactionsByAccount(String accountNumber) {
        System.out.println("TransactionService.getTransactionsByAccount()");
        return transactionRepository.findByAccount_AccountNumber(accountNumber)
        		    .stream()
	            .map(this::convertToResponse)
	            .toList();
    }

    public void deleteTransaction(Long id) {
        System.out.println("TransactionService.deleteTransaction()");
        Transaction transaction= transactionRepository.findById(id).orElseThrow(() ->
        new RuntimeException("Transaction not found"));
        
        transactionRepository.delete(transaction);
    }
}
