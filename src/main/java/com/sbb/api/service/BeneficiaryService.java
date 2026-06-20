package com.sbb.api.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sbb.api.dao.BeneficiaryRepository;
import com.sbb.api.dao.UserRepository;
import com.sbb.api.entity.Beneficiary;
import com.sbb.api.entity.User;

@Service
public class BeneficiaryService {

    @Autowired
    private BeneficiaryRepository beneficiaryRepository;

    @Autowired
    private UserRepository userRepository;

    public Beneficiary createBeneficiary(Long userId,Beneficiary beneficiary) {
        System.out.println("BeneficiaryService.createBeneficiary()");
        User user = userRepository.findById(userId).orElseThrow(() ->
                        new RuntimeException("User not found"));
        beneficiary.setUser(user);
        beneficiary.setCreatedAt(LocalDateTime.now());
        beneficiary.setUpdatedAt(LocalDateTime.now());
        return beneficiaryRepository.save(beneficiary);
    }

    public Beneficiary getBeneficiaryById(Long id) {
        System.out.println("BeneficiaryService.getBeneficiaryById()");
        return beneficiaryRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Beneficiary not found"));
    }

    public List<Beneficiary> getBeneficiariesByUserId(Long userId) {
        System.out.println("BeneficiaryService.getBeneficiariesByUserId()");
        return beneficiaryRepository.findByUserId(userId);
    }

    public Beneficiary updateBeneficiary(Long id,Beneficiary beneficiary) {
        System.out.println("BeneficiaryService.updateBeneficiary()");
        Beneficiary existingBeneficiary =getBeneficiaryById(id);

        existingBeneficiary.setBeneficiaryName(beneficiary.getBeneficiaryName());
        existingBeneficiary.setAccountNumber(beneficiary.getAccountNumber());
        existingBeneficiary.setBankName(beneficiary.getBankName());
        existingBeneficiary.setIfscCode(beneficiary.getIfscCode());
        existingBeneficiary.setNickname(beneficiary.getNickname());
        existingBeneficiary.setActive(beneficiary.getActive());
        existingBeneficiary.setUpdatedAt(LocalDateTime.now());

        return beneficiaryRepository.save(existingBeneficiary);
    }

    public void deleteBeneficiary(Long id) {
        System.out.println("BeneficiaryService.deleteBeneficiary()");
        Beneficiary beneficiary =getBeneficiaryById(id);
        beneficiaryRepository.delete(beneficiary);
    }
}
