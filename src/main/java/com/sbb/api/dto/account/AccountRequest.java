package com.sbb.api.dto.account;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AccountRequest {
	
	    @NotBlank(message = "Account Number is required.")
	    @Pattern(
	            regexp = "^[0-9]{9,18}$",
	            message = "Account Number must contain 9 to 18 digits."
	    )
	    private String accountNumber;

	    @NotBlank(message = "Account Type is required.")
	    @Pattern(
	            regexp = "^(SAVINGS|CURRENT)$",
	            message = "Account Type must be SAVINGS or CURRENT."
	    )
	    private String accountType;

	    @NotNull(message = "Minimum Balance is required.")
	    @DecimalMin(value = "0.00", message = "Minimum Balance cannot be negative.")
	    private BigDecimal minimumBalance;

	    @NotNull(message = "Daily Transfer Limit is required.")
	    @DecimalMin(value = "0.00", message = "Daily Transfer Limit cannot be negative.")
	    private BigDecimal dailyTransferLimit;

	    @NotNull(message = "Withdrawal Limit is required.")
	    @DecimalMin(value = "0.00", message = "Withdrawal Limit cannot be negative.")
	    private BigDecimal withdrawalLimit;

	    @NotBlank(message = "Branch Name is required.")
	    private String branchName;

	    @NotBlank(message = "IFSC Code is required.")
	    @Pattern(
	            regexp = "^[A-Z]{4}0[A-Z0-9]{6}$",
	            message = "Please enter a valid IFSC Code."
	    )
	    private String ifscCode;

	    @NotNull(message = "Customer Id is required.")
	    @Positive(message = "Customer Id must be greater than 0.")
	    private Long customerId;


}
