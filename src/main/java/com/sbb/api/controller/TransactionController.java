package com.sbb.api.controller;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sbb.api.entity.Transaction;
import com.sbb.api.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/deposit")
    public Transaction deposit(@RequestParam String accountNumber, @RequestParam BigDecimal amount, @RequestParam String remarks) {
        System.out.println("TransactionController.deposit()");
        return transactionService.deposit(accountNumber,amount,remarks);
    }

    @PostMapping("/withdraw")
    public Transaction withdraw(@RequestParam String accountNumber,@RequestParam BigDecimal amount,@RequestParam String remarks) {
        System.out.println("TransactionController.withdraw()");
        return transactionService.withdraw(accountNumber,amount,remarks);
    }

    @PostMapping("/transfer")
    public Transaction transfer(@RequestParam String fromAccount,@RequestParam String toAccount,@RequestParam BigDecimal amount,@RequestParam String remarks) {
        System.out.println("TransactionController.transfer()");
        return transactionService.transfer(fromAccount,toAccount,amount,remarks);
    }

    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable Long id) {
        System.out.println("TransactionController.getTransactionById()");
        return transactionService.getTransactionById(id);
    }

    @GetMapping
    public List<Transaction> getAllTransactions() {
        System.out.println("TransactionController.getAllTransactions()");
        return transactionService.getAllTransactions();
    }

    @GetMapping("/account/{accountNumber}")
    public List<Transaction> getTransactionsByAccount(@PathVariable String accountNumber) {
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