package com.sbb.api.dto.wallet;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class WalletRequest {
	
	@NotNull(message = "Daily Limit is required.")
    @DecimalMin(
            value = "0.01",
            message = "Daily Limit must be greater than 0."
    )
    @Digits(
            integer = 12,
            fraction = 2,
            message = "Daily Limit can have up to 12 integer digits and 2 decimal places."
    )
    private BigDecimal dailyLimit;

    @NotNull(message = "Customer Id is required.")
    @Positive(message = "Customer Id must be greater than 0.")
    private Long customerId;

}
