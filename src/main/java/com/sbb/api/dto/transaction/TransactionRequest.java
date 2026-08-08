package com.sbb.api.dto.transaction;

import java.math.BigDecimal;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class TransactionRequest {

	    @NotBlank(message = "Transaction Type is required.")
	    @Pattern(
	            regexp = "^(DEPOSIT|WITHDRAWAL|TRANSFER|PAYMENT)$",
	            message = "Transaction Type must be DEPOSIT, WITHDRAWAL, TRANSFER, or PAYMENT."
	    )
	    private String transactionType;

	    @NotNull(message = "Amount is required.")
	    @DecimalMin(
	            value = "0.01",
	            message = "Amount must be greater than 0."
	    )
	    @Digits(
	            integer = 12,
	            fraction = 2,
	            message = "Amount can have up to 12 integer digits and 2 decimal places."
	    )
	    private BigDecimal amount;

	    @NotBlank(message = "Remarks are required.")
	    private String remarks;

	    @NotNull(message = "accountNumber is required.")
	    @Pattern(
		         regexp = "^[0-9]{9,18}$",
		         message = "Account Number must contain 9 to 18 digits." )
	    private String accountNumber;
}
