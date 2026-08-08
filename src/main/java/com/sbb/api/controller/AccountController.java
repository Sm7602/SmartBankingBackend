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

import com.sbb.api.dto.account.AccountRequest;
import com.sbb.api.dto.account.AccountResponse;
import com.sbb.api.dto.account.AccountUpdateRequest;
import com.sbb.api.service.AccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public AccountResponse createAccount( @Valid @RequestBody AccountRequest request){
        System.out.println("AccountController.createAccount()");
        return accountService.createAccount(request);
    }

    @GetMapping("/{id}")
    public AccountResponse getAccountById(@PathVariable Long id) {
        System.out.println("AccountController.getAccountById()");
        return accountService.getAccountById(id);
    }

    @GetMapping
    public List<AccountResponse> getAllAccounts() {
        System.out.println("AccountController.getAllAccounts()");
        return accountService.getAllAccounts();
    }

    @PutMapping("/{id}")
    public AccountResponse updateAccount(Long id, @Valid @RequestBody AccountUpdateRequest request){
        System.out.println("AccountController.updateAccount()");
        return accountService.updateAccount(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteAccount(@PathVariable Long id) {
        System.out.println("AccountController.deleteAccount()");
        accountService.deleteAccount(id);
        return "Account Deleted Successfully";
    }

    @GetMapping("/number/{accountNumber}")
    public AccountResponse getAccountByNumber(@PathVariable String accountNumber) {
        System.out.println("AccountController.getAccountByNumber()");
        return accountService.getAccountByNumber(accountNumber);
    }

    @GetMapping("/balance/{accountNumber}")
    public BigDecimal getAccountBalance(@PathVariable String accountNumber) {
        System.out.println("AccountController.getAccountBalance()");
        return accountService.getAccountBalance(accountNumber);
    }

    @PutMapping("/{id}/activate")
    public AccountResponse activateAccount(@PathVariable Long id) {
        System.out.println("AccountController.activateAccount()");
        return accountService.activateAccount(id);
    }

    @PutMapping("/{id}/deactivate")
    public AccountResponse deactivateAccount(@PathVariable Long id) {
        System.out.println("AccountController.deactivateAccount()");
        return accountService.deactivateAccount(id);
    }
}
