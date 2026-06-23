package com.sbb.api.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sbb.api.dao.UserRepository;
import com.sbb.api.dao.WalletRepository;
import com.sbb.api.entity.User;
import com.sbb.api.entity.Wallet;
import jakarta.transaction.Transactional;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserRepository userRepository;

    public Wallet createWallet(Long userId, Wallet wallet) {
        System.out.println("WalletService.createWallet()");
        User user = userRepository.findById(userId).orElseThrow(() ->
                        new RuntimeException("User not found"));

        wallet.setUser(user);
        wallet.setWalletNumber("WAL" + System.currentTimeMillis());
        wallet.setWalletBalance(BigDecimal.ZERO);
        wallet.setCreatedAt(LocalDateTime.now());
        wallet.setUpdatedAt(LocalDateTime.now());

        return walletRepository.save(wallet);
    }

    public Wallet getWalletById(Long id) {
        System.out.println("WalletService.getWalletById()");
        return walletRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Wallet not found"));
    }

    public Wallet addMoney(Long walletId,BigDecimal amount) {
        System.out.println("WalletService.addMoney()");
        Wallet wallet = getWalletById(walletId);

        if(!wallet.getActive()) {
            throw new RuntimeException("Wallet is inactive");
        }
        
        if(amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Invalid amount");
        }

        wallet.setWalletBalance(wallet.getWalletBalance().add(amount));
        wallet.setUpdatedAt(LocalDateTime.now());

        return walletRepository.save(wallet);
    }

    public Wallet withdrawMoney(Long walletId,BigDecimal amount) {
        System.out.println("WalletService.withdrawMoney()");
        Wallet wallet = getWalletById(walletId);

        if(!wallet.getActive()) {
            throw new RuntimeException("Wallet is inactive");
        }
        
        if(amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Invalid amount");
        }

        if(wallet.getWalletBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient wallet balance");
        }
        
        if(amount.compareTo(wallet.getDailyLimit()) > 0) {
            throw new RuntimeException("Transfer limit exceeded");
        }

        wallet.setWalletBalance(wallet.getWalletBalance().subtract(amount));
        wallet.setUpdatedAt(LocalDateTime.now());

        return walletRepository.save(wallet);
    }

    @Transactional
    public Wallet transferMoney(Long senderWalletId,Long receiverWalletId,BigDecimal amount) {
        System.out.println("WalletService.transferMoney()");
        Wallet sender =getWalletById(senderWalletId);
        Wallet receiver =getWalletById(receiverWalletId);
        
        if(!sender.getActive()) {
            throw new RuntimeException("Sender account inactive");
        }

        if(!receiver.getActive()) {
            throw new RuntimeException("Receiver account inactive");
        }
        
        if(senderWalletId.equals(receiverWalletId)) {
            throw new RuntimeException("Cannot transfer to same account");
        }
        
        if(amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Invalid amount");
        }
        
        if(sender.getWalletBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }
        
        if(amount.compareTo(sender.getDailyLimit()) > 0) {
            throw new RuntimeException("Transfer limit exceeded");
        }

        sender.setWalletBalance(sender.getWalletBalance().subtract(amount));
        receiver.setWalletBalance(receiver.getWalletBalance().add(amount));

        walletRepository.save(sender);
        walletRepository.save(receiver);

        return sender;
    }

    public void deleteWallet(Long id) {
        System.out.println("WalletService.deleteWallet()");
        Wallet wallet = getWalletById(id);
        User user = wallet.getUser();
        if(user != null) {
            user.setWallet(null);
            userRepository.save(user);
        }
        wallet.setUser(null);
        walletRepository.delete(wallet);
    }
}
