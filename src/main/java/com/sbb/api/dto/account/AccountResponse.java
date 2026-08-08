package com.sbb.api.dto.account;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.sbb.api.entity.Customer;
import com.sbb.api.entity.Transaction;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountResponse {
	
	    private Long id;

	    private String accountNumber;

	    private String accountType;

	    private BigDecimal balance;

	    private BigDecimal minimumBalance;

	    private BigDecimal dailyTransferLimit;

	    private BigDecimal withdrawalLimit;

	    private Boolean active;

	    private String branchName;

	    private String ifscCode;

	    private LocalDateTime openedAt;

	    private LocalDateTime updatedAt;

	    private Customer customer;

	    private List<Transaction> transactions;

}
