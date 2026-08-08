package com.sbb.api.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sbb.api.dao.BeneficiaryRepository;
import com.sbb.api.dao.CustomerRepository;
import com.sbb.api.dto.beneficiary.BeneficiaryRequest;
import com.sbb.api.dto.beneficiary.BeneficiaryResponse;
import com.sbb.api.dto.beneficiary.BeneficiaryUpdateRequest;
import com.sbb.api.entity.Beneficiary;
import com.sbb.api.entity.Customer;

@Service
public class BeneficiaryService {

    @Autowired
    private BeneficiaryRepository beneficiaryRepository;

    @Autowired
    private CustomerRepository customerRepository;
    
    private BeneficiaryResponse convertToResponse(Beneficiary beneficiary) {

        return BeneficiaryResponse.builder()
                .id(beneficiary.getId())
                .beneficiaryName(beneficiary.getBeneficiaryName())
                .accountNumber(beneficiary.getAccountNumber())
                .bankName(beneficiary.getBankName())
                .nickname(beneficiary.getNickname())
                .active(beneficiary.getActive())
                .ifscCode(beneficiary.getIfscCode())
                .updatedAt(beneficiary.getUpdatedAt())
                .createdAt(beneficiary.getCreatedAt())
                .customer(beneficiary.getCustomer())
                .user(beneficiary.getUser())
                .build();
    }

    public BeneficiaryResponse createBeneficiary(BeneficiaryRequest request) {
        System.out.println("BeneficiaryService.createBeneficiary()");
        Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow(() ->
                        new RuntimeException("Customer not found"));
       
        Beneficiary beneficiary=Beneficiary.builder()
        		    .beneficiaryName(request.getBeneficiaryName())
                .accountNumber(request.getAccountNumber())
                .bankName(request.getBankName())
                .nickname(request.getNickname())
                .active(true)
                .ifscCode(request.getIfscCode())
                .updatedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .customer(customer)
                .build();
        
        beneficiary= beneficiaryRepository.save(beneficiary);
        return convertToResponse(beneficiary);
    }

    public BeneficiaryResponse getBeneficiaryById(Long id) {
        System.out.println("BeneficiaryService.getBeneficiaryById()");
        Beneficiary beneficiary= beneficiaryRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Beneficiary not found"));
        return convertToResponse(beneficiary);
    }

    public List<BeneficiaryResponse> getBeneficiariesByCustomerId(Long customerId) {
        System.out.println("BeneficiaryService.getBeneficiariesByCustomerId()");
        return beneficiaryRepository.findByCustomerId(customerId)
        		    .stream()
	            .map(this::convertToResponse)
	            .toList();
    }

    public BeneficiaryResponse updateBeneficiary(Long id,BeneficiaryUpdateRequest request) {
        System.out.println("BeneficiaryService.updateBeneficiary()");
        Beneficiary existingBeneficiary =beneficiaryRepository.findById(id).orElseThrow(() ->
        new RuntimeException("Beneficiary not found"));

        existingBeneficiary.setBeneficiaryName(request.getBeneficiaryName());
        existingBeneficiary.setAccountNumber(request.getAccountNumber());
        existingBeneficiary.setBankName(request.getBankName());
        existingBeneficiary.setIfscCode(request.getIfscCode());
        existingBeneficiary.setNickname(request.getNickname());
        existingBeneficiary.setActive(request.getActive());
        existingBeneficiary.setUpdatedAt(LocalDateTime.now());

        existingBeneficiary= beneficiaryRepository.save(existingBeneficiary);
        return convertToResponse(existingBeneficiary);
    }

    public void deleteBeneficiary(Long id) {
        System.out.println("BeneficiaryService.deleteBeneficiary()");
        Beneficiary beneficiary= beneficiaryRepository.findById(id).orElseThrow(() ->
        new RuntimeException("Beneficiary not found"));
        beneficiaryRepository.delete(beneficiary);
    }
}
