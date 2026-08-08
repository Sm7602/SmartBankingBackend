package com.sbb.api.dto.customer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import com.sbb.api.entity.Account;
import com.sbb.api.entity.Beneficiary;
import com.sbb.api.entity.ScheduledPayment;
import com.sbb.api.entity.User;
import com.sbb.api.entity.Wallet;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerResponse {

	private Long id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String address;

    private String city;

    private String state;

    private String pincode;

    private LocalDate dateOfBirth;
    
    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<Account> accounts;

    private List<Beneficiary> beneficiaries;

    private List<ScheduledPayment> scheduledPayments;

    private Wallet wallet;

    private User user;

}
