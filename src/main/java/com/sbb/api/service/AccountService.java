package com.sbb.api.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sbb.api.dao.AccountRepository;
import com.sbb.api.dao.UserRepository;
import com.sbb.api.entity.Account;
import com.sbb.api.entity.User;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    public Account createAccount(Long userId, Account account) {
        System.out.println("AccountService.createAccount()");
        User user = userRepository.findById(userId).orElseThrow(() ->
                        new RuntimeException("User not found"));

        account.setUser(user);
        account.setOpenedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());
        return accountRepository.save(account);
    }

    public Account getAccountById(Long id) {
        System.out.println("AccountService.getAccountById()");
        return accountRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Account not found"));
    }

    public List<Account> getAllAccounts() {
        System.out.println("AccountService.getAllAccounts()");
        return accountRepository.findAll();
    }

    public Account updateAccount(Long id, Account account) {
        System.out.println("AccountService.updateAccount()");
        Account existingAccount = getAccountById(id);

        existingAccount.setAccountType(account.getAccountType());
        existingAccount.setBalance(account.getBalance());
        existingAccount.setMinimumBalance(account.getMinimumBalance());
        existingAccount.setDailyTransferLimit(account.getDailyTransferLimit());
        existingAccount.setWithdrawalLimit(account.getWithdrawalLimit());
        existingAccount.setActive(account.getActive());
        existingAccount.setBranchName(account.getBranchName());
        existingAccount.setIfscCode(account.getIfscCode());
        existingAccount.setUpdatedAt(LocalDateTime.now());

        return accountRepository.save(existingAccount);
    }

    public void deleteAccount(Long id) {
        System.out.println("AccountService.deleteAccount()");
        Account account = getAccountById(id);
        accountRepository.delete(account);
    }

    public Account getAccountByNumber(String accountNumber) {
        System.out.println("AccountService.getAccountByNumber()");
        return accountRepository.findByAccountNumber(accountNumber).orElseThrow(() ->
                        new RuntimeException("Account not found"));
    }

    public BigDecimal getAccountBalance(String accountNumber) {
        System.out.println("AccountService.getAccountBalance()");
        Account account = getAccountByNumber(accountNumber);
        return account.getBalance();
    }

    public Account activateAccount(Long id) {
        System.out.println("AccountService.activateAccount()");
        Account account = getAccountById(id);
        account.setActive(true);
        account.setUpdatedAt(LocalDateTime.now());
        return accountRepository.save(account);
    }

    public Account deactivateAccount(Long id) {
        System.out.println("AccountService.deactivateAccount()");
        Account account = getAccountById(id);
        account.setActive(false);
        account.setUpdatedAt(LocalDateTime.now());
        return accountRepository.save(account);
    }
}
