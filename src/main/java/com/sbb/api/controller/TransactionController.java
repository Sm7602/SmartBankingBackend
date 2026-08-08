package com.sbb.api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbb.api.dto.transaction.TransactionRequest;
import com.sbb.api.dto.transaction.TransactionResponse;
import com.sbb.api.dto.transaction.TransferRequest;
import com.sbb.api.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/deposit")
    public TransactionResponse deposit(TransactionRequest request)  {
        System.out.println("TransactionController.deposit()");
        return transactionService.deposit(request);
    }

    @PostMapping("/withdraw")
    public TransactionResponse withdraw(TransactionRequest request) {
        System.out.println("TransactionController.withdraw()");
        return transactionService.withdraw(request);
    }

    @PostMapping("/transfer")
    public TransactionResponse transfer(TransferRequest request) {
        System.out.println("TransactionController.transfer()");
        return transactionService.transfer(request);
    }

    @GetMapping("/{id}")
    public TransactionResponse getTransactionById(@PathVariable Long id) {
        System.out.println("TransactionController.getTransactionById()");
        return transactionService.getTransactionById(id);
    }

    @GetMapping
    public List<TransactionResponse> getAllTransactions() {
        System.out.println("TransactionController.getAllTransactions()");
        return transactionService.getAllTransactions();
    }

    @GetMapping("/account/{accountNumber}")
    public List<TransactionResponse> getTransactionsByAccount(@PathVariable String accountNumber) {
        System.out.println("TransactionController.getTransactionsByAccount()");
        return transactionService.getTransactionsByAccount(accountNumber);
    }

    @DeleteMapping("/{id}")
    public String deleteTransaction(@PathVariable Long id) {
        System.out.println("TransactionController.deleteTransaction()");
        transactionService.deleteTransaction(id);
        return "Transaction Deleted Successfully";
    }
}