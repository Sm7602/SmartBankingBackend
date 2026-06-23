package com.sbb.api.controller;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sbb.api.entity.Wallet;
import com.sbb.api.service.WalletService;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PostMapping("/{userId}")
    public Wallet createWallet(@PathVariable Long userId,@RequestBody Wallet wallet) {
        System.out.println("WalletController.createWallet()");
        return walletService.createWallet(userId, wallet);
    }

    @GetMapping("/{id}")
    public Wallet getWalletById(@PathVariable Long id) {
        System.out.println("WalletController.getWalletById()");
        return walletService.getWalletById(id);
    }

    @PostMapping("/{id}/add-money")
    public Wallet addMoney(@PathVariable Long id, @RequestParam BigDecimal amount) {
        System.out.println("WalletController.addMoney()");
        return walletService.addMoney(id, amount);
    }

    @PostMapping("/{id}/withdraw")
    public Wallet withdrawMoney(@PathVariable Long id,@RequestParam BigDecimal amount) {
        System.out.println("WalletController.withdrawMoney()");
        return walletService.withdrawMoney(id, amount);
    }

    @PostMapping("/{id}/transfer")
    public Wallet transferMoney(@PathVariable Long id,@RequestParam Long receiverWalletId,@RequestParam BigDecimal amount) {
        System.out.println("WalletController.transferMoney()");
        return walletService.transferMoney(id,receiverWalletId,amount);
    }

    @DeleteMapping("/{id}")
    public String deleteWallet(@PathVariable Long id) {
        System.out.println("WalletController.deleteWallet()");
        walletService.deleteWallet(id);
        return "Wallet Deleted Successfully";
    }
}