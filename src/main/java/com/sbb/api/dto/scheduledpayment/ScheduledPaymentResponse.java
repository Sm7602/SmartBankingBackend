package com.sbb.api.dto.scheduledpayment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.sbb.api.entity.Customer;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ScheduledPaymentResponse {
	
	private Long id;

    private String paymentReference;

    private String paymentTitle;

    private BigDecimal amount;

    private String beneficiaryAccountNumber;

    private String ifscCode;

    private LocalDate nextPaymentDate;

    private String frequency;

    private Boolean active;

    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;

    private Customer customer;

}
