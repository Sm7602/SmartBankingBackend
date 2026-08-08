package com.sbb.api.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sbb.api.dao.AccountRepository;
import com.sbb.api.dao.CustomerRepository;
import com.sbb.api.dto.account.AccountRequest;
import com.sbb.api.dto.account.AccountResponse;
import com.sbb.api.dto.account.AccountUpdateRequest;
import com.sbb.api.entity.Account;
import com.sbb.api.entity.Customer;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;
    
    private AccountResponse convertToResponse(Account account) {

        return AccountResponse.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .balance(account.getBalance())
                .minimumBalance(account.getMinimumBalance())
                .dailyTransferLimit(account.getDailyTransferLimit())
                .withdrawalLimit(account.getWithdrawalLimit())
                .active(account.getActive())
                .branchName(account.getBranchName())
                .ifscCode(account.getIfscCode())
                .openedAt(account.getOpenedAt())
                .updatedAt(account.getUpdatedAt())
                .customer(account.getCustomer())
                .transactions(account.getTransactions())
                .build();
    }

    public AccountResponse createAccount(AccountRequest request) {
        System.out.println("AccountService.createAccount()");
        Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        Account account=Account.builder()
        		    .accountNumber(request.getAccountNumber())
                .accountType(request.getAccountType())
                .balance(BigDecimal.ZERO)
                .minimumBalance(request.getMinimumBalance())
                .dailyTransferLimit(request.getDailyTransferLimit())
                .withdrawalLimit(request.getWithdrawalLimit())
                .active(true)
                .branchName(request.getBranchName())
                .ifscCode(request.getIfscCode())
                .openedAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .customer(customer)
        		   .build();
        
        account= accountRepository.save(account);
        return convertToResponse(account);
    }

    public AccountResponse getAccountById(Long id) {
        System.out.println("AccountService.getAccountById()");
        Account account = accountRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Account not found"));
        
        return convertToResponse(account);
    }

    public List<AccountResponse> getAllAccounts() {
        System.out.println("AccountService.getAllAccounts()");
        return accountRepository.findAll()
        		    .stream()
	            .map(this::convertToResponse)
	            .toList();
    }

    public AccountResponse updateAccount(Long id, AccountUpdateRequest request) {
        System.out.println("AccountService.updateAccount()");
        Account existingAccount =accountRepository.findById(id).orElseThrow(() ->
        new RuntimeException("Account not found"));

        existingAccount.setAccountType(request.getAccountType());
        existingAccount.setBalance(request.getBalance());
        existingAccount.setMinimumBalance(request.getMinimumBalance());
        existingAccount.setDailyTransferLimit(request.getDailyTransferLimit());
        existingAccount.setWithdrawalLimit(request.getWithdrawalLimit());
        existingAccount.setActive(request.getActive());
        existingAccount.setBranchName(request.getBranchName());
        existingAccount.setIfscCode(request.getIfscCode());
        existingAccount.setUpdatedAt(LocalDateTime.now());

        existingAccount= accountRepository.save(existingAccount);
        return convertToResponse(existingAccount);
    }

    public void deleteAccount(Long id) {
        System.out.println("AccountService.deleteAccount()");
        Account account = accountRepository.findById(id).orElseThrow(() ->
        new RuntimeException("Account not found"));
        accountRepository.delete(account);
    }

    public AccountResponse getAccountByNumber(String accountNumber) {
        System.out.println("AccountService.getAccountByNumber()");
        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() ->
                        new RuntimeException("Account not found"));
        return convertToResponse(account);
    }

    public BigDecimal getAccountBalance(String accountNumber) {
        System.out.println("AccountService.getAccountBalance()");
        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() ->
        new RuntimeException("Account not found"));
        return account.getBalance();
    }

    public AccountResponse activateAccount(Long id) {
        System.out.println("AccountService.activateAccount()");
        Account account = accountRepository.findById(id).orElseThrow(() ->
        new RuntimeException("Account not found"));
        account.setActive(true);
        account.setUpdatedAt(LocalDateTime.now());

        account= accountRepository.save(account);
        return convertToResponse(account);
    }

    public AccountResponse deactivateAccount(Long id) {
        System.out.println("AccountService.deactivateAccount()");
        Account account = accountRepository.findById(id).orElseThrow(() ->
        new RuntimeException("Account not found"));
        account.setActive(false);
        account.setUpdatedAt(LocalDateTime.now());

        account= accountRepository.save(account);
        return convertToResponse(account);
    }
}
