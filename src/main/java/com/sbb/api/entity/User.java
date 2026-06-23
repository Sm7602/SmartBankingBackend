package com.sbb.api.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String address;

    private String city;

    private String state;

    private String pincode;

    private String customerId;

    private LocalDate dateOfBirth;
    
    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "user")
    private List<Account> accounts;
    
    @OneToMany(mappedBy = "user")
    private List<Beneficiary> beneficiaries;
    
    @OneToMany(mappedBy = "user")
    private List<ScheduledPayment> scheduledPayments;
    
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
  private Wallet wallet;
    
  
}