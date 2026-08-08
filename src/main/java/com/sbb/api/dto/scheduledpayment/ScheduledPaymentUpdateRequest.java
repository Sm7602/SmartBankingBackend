package com.sbb.api.dto.scheduledpayment;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class ScheduledPaymentUpdateRequest {
	
	 private String beneficiaryAccountNumber;
	 
	 private String frequency;

	 private BigDecimal amount;
	 
	 private LocalDate nextPaymentDate;
	 
	 private Boolean active;

}
