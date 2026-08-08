package com.sbb.api.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sbb.api.dao.CustomerRepository;
import com.sbb.api.dao.WalletRepository;
import com.sbb.api.dto.wallet.TransectionRequest;
import com.sbb.api.dto.wallet.TransferRequest;
import com.sbb.api.dto.wallet.WalletRequest;
import com.sbb.api.dto.wallet.WalletResponse;
import com.sbb.api.entity.Customer;
import com.sbb.api.entity.Wallet;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private CustomerRepository customerRepository;
    
    private WalletResponse convertToResponse(Wallet wallet) {

        return WalletResponse.builder()
                .id(wallet.getId())
                .walletNumber(wallet.getWalletNumber())
                .walletBalance(wallet.getWalletBalance())
                .dailyLimit(wallet.getDailyLimit())
                .active(wallet.getActive())
                .createdAt(wallet.getCreatedAt())
                .updatedAt(wallet.getUpdatedAt())
                .customer(wallet.getCustomer())
                .build();
    }

    public WalletResponse createWallet(WalletRequest request) {
        System.out.println("WalletService.createWallet()");
        Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow(() ->
                        new RuntimeException("Customer not found"));
        
        Wallet wallet=Wallet.builder()
        		    .walletNumber("WAL" + System.currentTimeMillis())
                .walletBalance(BigDecimal.ZERO)
                .dailyLimit(request.getDailyLimit())
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .customer(customer)
                .build();

        wallet= walletRepository.save(wallet);
        return convertToResponse(wallet);
    }

    public WalletResponse getWalletById(Long id) {
        System.out.println("WalletService.getWalletById()");
        Wallet wallet= walletRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Wallet not found"));
        
        return convertToResponse(wallet);
    }

    public WalletResponse addMoney(TransectionRequest request) {
        System.out.println("WalletService.addMoney()");
        Wallet wallet = walletRepository.findById(request.getWalletId()).orElseThrow(() ->
        new RuntimeException("Wallet not found"));

        if(!wallet.getActive()) {
            throw new RuntimeException("Wallet is inactive");
        }
        
        if(request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Invalid amount");
        }

        wallet.setWalletBalance(wallet.getWalletBalance().add(request.getAmount()));
        wallet.setUpdatedAt(LocalDateTime.now());

        wallet= walletRepository.save(wallet);
        return convertToResponse(wallet);
    }

    public WalletResponse withdrawMoney(TransectionRequest request) {
        System.out.println("WalletService.withdrawMoney()");
        Wallet wallet = walletRepository.findById(request.getWalletId()).orElseThrow(() ->
        new RuntimeException("Wallet not found"));

        if(!wallet.getActive()) {
            throw new RuntimeException("Wallet is inactive");
        }
        
        if(request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Invalid amount");
        }

        if(wallet.getWalletBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient wallet balance");
        }
        
        LocalDate today = LocalDate.now();

        LocalDateTime startOfDay =today.atStartOfDay();

        LocalDateTime endOfDay =today.plusDays(1).atStartOfDay();
        
        BigDecimal dailyTransferred = walletRepository.getTodayTransferAmount(wallet.getId(),startOfDay,endOfDay);

        if (dailyTransferred == null) {
            dailyTransferred = BigDecimal.ZERO;
        }

        BigDecimal dailyLimit =wallet.getDailyLimit();

        if (dailyLimit != null &&
                dailyTransferred.add(request.getAmount()).compareTo(dailyLimit) > 0) {

            throw new RuntimeException("Daily transfer limit exceeded");
        }
        

        wallet.setWalletBalance(wallet.getWalletBalance().subtract(request.getAmount()));
        wallet.setUpdatedAt(LocalDateTime.now());

        wallet= walletRepository.save(wallet);
        return convertToResponse(wallet);
    }

    public WalletResponse transferMoney(TransferRequest request) {
        System.out.println("WalletService.transferMoney()");
        
        Wallet sender =walletRepository.findById(request.getSenderwalletId()) .orElseThrow(() ->
        new RuntimeException("Sender account not found"));
        
        Wallet receiver =walletRepository.findById(request.getReciverwalletId()) .orElseThrow(() ->
        new RuntimeException("Sender account not found"));
        
        if(!sender.getActive()) {
            throw new RuntimeException("Sender account inactive");
        }

        if(!receiver.getActive()) {
            throw new RuntimeException("Receiver account inactive");
        }
        
        if(request.getSenderwalletId().equals(request.getReciverwalletId())) {
            throw new RuntimeException("Cannot transfer to same account");
        }
        
        if(request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Invalid amount");
        }
        
        if(sender.getWalletBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance");
        }
        
        LocalDate today = LocalDate.now();

        LocalDateTime startOfDay =today.atStartOfDay();

        LocalDateTime endOfDay =today.plusDays(1).atStartOfDay();
        
        BigDecimal dailyTransferred = walletRepository.getTodayTransferAmount(sender.getId(),startOfDay,endOfDay);

        if (dailyTransferred == null) {
            dailyTransferred = BigDecimal.ZERO;
        }

        BigDecimal dailyLimit =sender.getDailyLimit();

        if (dailyLimit != null &&
                dailyTransferred.add(request.getAmount()).compareTo(dailyLimit) > 0) {

            throw new RuntimeException("Daily transfer limit exceeded");
        }

        sender.setWalletBalance(sender.getWalletBalance().subtract(request.getAmount()));
        receiver.setWalletBalance(receiver.getWalletBalance().add(request.getAmount()));

        walletRepository.save(sender);
        walletRepository.save(receiver);

        return convertToResponse(sender);
    }

    public void deleteWallet(Long id) {
        System.out.println("WalletService.deleteWallet()");
        Wallet wallet = walletRepository.findById(id).orElseThrow(() ->
        new RuntimeException("Wallet not found"));
        Customer customer = wallet.getCustomer();
        if(customer != null) {
            customer.setWallet(null);
            customerRepository.save(customer);
        }
        wallet.setCustomer(null);
        walletRepository.delete(wallet);
    }
}
