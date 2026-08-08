package com.sbb.api.dto.wallet;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class TransectionRequest {
	
	@NotNull(message = "walletId is required.")
    @Positive(message = "walletId must be greater than 0.")
    private Long walletId;
	
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

}
