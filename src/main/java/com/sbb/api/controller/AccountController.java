package com.sbb.api.controller;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sbb.api.entity.Account;
import com.sbb.api.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/userId/{userId}")
    public Account createAccount(@PathVariable Long userId,@RequestBody Account account) {
        System.out.println("AccountController.createAccount()");
        return accountService.createAccount(userId, account);
    }

    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable Long id) {
        System.out.println("AccountController.getAccountById()");
        return accountService.getAccountById(id);
    }

    @GetMapping
    public List<Account> getAllAccounts() {
        System.out.println("AccountController.getAllAccounts()");
        return accountService.getAllAccounts();
    }

    @PutMapping("/{id}")
    public Account updateAccount(@PathVariable Long id,@RequestBody Account account) {
        System.out.println("AccountController.updateAccount()");
        return accountService.updateAccount(id, account);
    }

    @DeleteMapping("/{id}")
    public String deleteAccount(@PathVariable Long id) {
        System.out.println("AccountController.deleteAccount()");
        accountService.deleteAccount(id);
        return "Account Deleted Successfully";
    }

    @GetMapping("/number/{accountNumber}")
    public Account getAccountByNumber(@PathVariable String accountNumber) {
        System.out.println("AccountController.getAccountByNumber()");
        return accountService.getAccountByNumber(accountNumber);
    }

    @GetMapping("/balance/{accountNumber}")
    public BigDecimal getAccountBalance(@PathVariable String accountNumber) {
        System.out.println("AccountController.getAccountBalance()");
        return accountService.getAccountBalance(accountNumber);
    }

    @PutMapping("/{id}/activate")
    public Account activateAccount(@PathVariable Long id) {
        System.out.println("AccountController.activateAccount()");
        return accountService.activateAccount(id);
    }

    @PutMapping("/{id}/deactivate")
    public Account deactivateAccount(@PathVariable Long id) {
        System.out.println("AccountController.deactivateAccount()");
        return accountService.deactivateAccount(id);
    }
}
