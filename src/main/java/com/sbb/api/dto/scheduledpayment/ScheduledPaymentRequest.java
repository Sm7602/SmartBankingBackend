package com.sbb.api.dto.scheduledpayment;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ScheduledPaymentRequest {
	

    @NotBlank(message = "Payment Title is required.")
    private String paymentTitle;

    @NotNull(message = "Amount is required.")
    @DecimalMin(
            value = "1.00",
            message = "Amount must be greater than 0.")
    @Digits(
            integer = 12,
            fraction = 2,
            message = "Amount can have up to 12 integer digits and 2 decimal places."
    )
    private BigDecimal amount;

    @NotBlank(message = "Beneficiary Account Number is required.")
    @Pattern(
            regexp = "^[0-9]{9,18}$",
            message = "Beneficiary Account Number must contain 9 to 18 digits."
    )
    private String beneficiaryAccountNumber;

    @NotBlank(message = "IFSC Code is required.")
    @Pattern(
            regexp = "^[A-Z]{4}0[A-Z0-9]{6}$",
            message = "Please enter a valid IFSC Code."
    )
    private String ifscCode;

    @NotNull(message = "Next Payment Date is required.")
    @FutureOrPresent(message = "Next Payment Date cannot be in the past.")
    private LocalDate nextPaymentDate;

    @NotBlank(message = "Frequency is required.")
    @Pattern(
            regexp = "^(DAILY|WEEKLY|MONTHLY|QUARTERLY|YEARLY)$",
            message = "Frequency must be DAILY, WEEKLY, MONTHLY, QUARTERLY, or YEARLY."
    )
    private String frequency;

    @NotNull(message = "Customer Id is required.")
    @Positive(message = "Customer Id must be greater than 0.")
    private Long customerId;

}
