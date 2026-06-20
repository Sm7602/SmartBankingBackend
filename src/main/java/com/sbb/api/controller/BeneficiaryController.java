package com.sbb.api.controller;

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
import com.sbb.api.entity.Beneficiary;
import com.sbb.api.service.BeneficiaryService;

@RestController
@RequestMapping("/api/beneficiaries")
public class BeneficiaryController {

    @Autowired
    private BeneficiaryService beneficiaryService;

    @PostMapping("/{userId}")
    public Beneficiary createBeneficiary(@PathVariable Long userId,@RequestBody Beneficiary beneficiary) {
        System.out.println("BeneficiaryController.createBeneficiary()");
        return beneficiaryService.createBeneficiary(userId, beneficiary);
    }

    @GetMapping("/{id}")
    public Beneficiary getBeneficiaryById(@PathVariable Long id) {
        System.out.println("BeneficiaryController.getBeneficiaryById()");
        return beneficiaryService.getBeneficiaryById(id);
    }

    @GetMapping("/user/{userId}")
    public List<Beneficiary> getBeneficiariesByUserId(@PathVariable Long userId) {
        System.out.println("BeneficiaryController.getBeneficiariesByUserId()");
        return beneficiaryService.getBeneficiariesByUserId(userId);
    }

    @PutMapping("/{id}")
    public Beneficiary updateBeneficiary(@PathVariable Long id,@RequestBody Beneficiary beneficiary) {
        System.out.println("BeneficiaryController.updateBeneficiary()");
        return beneficiaryService.updateBeneficiary(id, beneficiary);
    }

    @DeleteMapping("/{id}")
    public String deleteBeneficiary(@PathVariable Long id) {
        System.out.println("BeneficiaryController.deleteBeneficiary()");
        beneficiaryService.deleteBeneficiary(id);
        return "Beneficiary Deleted Successfully";
    }
}