package com.sbb.api.dto.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.sbb.api.entity.Account;
import lombok.Builder;
import lombok.Data;

@Data 
@Builder 
public class TransactionResponse {
	
	    private Long id;

	    private String transactionReference;

	    private String transactionType;

	    private BigDecimal amount;

	    private BigDecimal availableBalance;

	    private String remarks;

	    private String status;

	    private LocalDateTime transactionTime;

	    private Account account;

}
