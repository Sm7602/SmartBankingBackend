package com.sbb.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sbb.api.dto.wallet.TransectionRequest;
import com.sbb.api.dto.wallet.TransferRequest;
import com.sbb.api.dto.wallet.WalletRequest;
import com.sbb.api.dto.wallet.WalletResponse;
import com.sbb.api.service.WalletService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PostMapping
    public WalletResponse createWallet(@Valid @RequestBody WalletRequest request) {
        System.out.println("WalletController.createWallet()");
        return walletService.createWallet(request);
    }

    @GetMapping("/{id}")
    public WalletResponse getWalletById(@PathVariable Long id) {
        System.out.println("WalletController.getWalletById()");
        return walletService.getWalletById(id);
    }

    @PostMapping("/add-money")
    public WalletResponse addMoney(@Valid @RequestBody TransectionRequest request)  {
        System.out.println("WalletController.addMoney()");
        return walletService.addMoney(request);
    }

    @PostMapping("/withdraw")
    public WalletResponse withdrawMoney(@Valid @RequestBody TransectionRequest request) {
        System.out.println("WalletController.withdrawMoney()");
        return walletService.withdrawMoney(request);
    }

    @PostMapping("/transfer")
    public WalletResponse transferMoney(@Valid @RequestBody TransferRequest request) {
        System.out.println("WalletController.transferMoney()");
        return walletService.transferMoney(request);
    }

    @DeleteMapping("/{id}")
    public String deleteWallet(@PathVariable Long id) {
        System.out.println("WalletController.deleteWallet()");
        walletService.deleteWallet(id);
        return "Wallet Deleted Successfully";
    }
}