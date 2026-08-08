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
import com.sbb.api.dto.beneficiary.BeneficiaryRequest;
import com.sbb.api.dto.beneficiary.BeneficiaryResponse;
import com.sbb.api.dto.beneficiary.BeneficiaryUpdateRequest;
import com.sbb.api.service.BeneficiaryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/beneficiaries")
public class BeneficiaryController {

    @Autowired
    private BeneficiaryService beneficiaryService;

    @PostMapping
    public BeneficiaryResponse createBeneficiary(@Valid @RequestBody BeneficiaryRequest request) {
        System.out.println("BeneficiaryController.createBeneficiary()");
        return beneficiaryService.createBeneficiary(request);
    }

    @GetMapping("/{id}")
    public BeneficiaryResponse getBeneficiaryById(@PathVariable Long id) {
        System.out.println("BeneficiaryController.getBeneficiaryById()");
        return beneficiaryService.getBeneficiaryById(id);
    }

    @GetMapping("/customer/{customerId}")
    public List<BeneficiaryResponse> getBeneficiariesByCustomerId(@PathVariable Long customerId) {
        System.out.println("BeneficiaryController.getBeneficiariesByCustomerId()");
        return beneficiaryService.getBeneficiariesByCustomerId(customerId);
    }

    @PutMapping("/{id}")
    public BeneficiaryResponse updateBeneficiary(@PathVariable Long id,@Valid @RequestBody BeneficiaryUpdateRequest request) {
        System.out.println("BeneficiaryController.updateBeneficiary()");
        return beneficiaryService.updateBeneficiary(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteBeneficiary(@PathVariable Long id) {
        System.out.println("BeneficiaryController.deleteBeneficiary()");
        beneficiaryService.deleteBeneficiary(id);
        return "Beneficiary Deleted Successfully";
    }
}