package com.sbb.api.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BeneficiaryRegisterRequest extends BaseRegisterRequest {

	    @NotBlank(message = "Beneficiary Name is required.")
	    @Pattern(
	            regexp = "^[A-Za-z ]{2,50}$",
	            message = "Beneficiary Name must contain only letters and be 2 to 50 characters long."
	    )
	    private String beneficiaryName;

	    @NotBlank(message = "Account Number is required.")
	    @Pattern(
	            regexp = "^[0-9]{9,18}$",
	            message = "Account Number must contain 9 to 18 digits."
	    )
	    private String accountNumber;

	    @NotBlank(message = "Bank Name is required.")
	    private String bankName;

	    @NotBlank(message = "IFSC Code is required.")
	    @Pattern(
	            regexp = "^[A-Z]{4}0[A-Z0-9]{6}$",
	            message = "Please enter a valid IFSC Code."
	    )
	    private String ifscCode;

	    @NotBlank(message = "Nickname is required.")
	    private String nickname;

	    @NotNull(message = "Customer Id is required.")
	    @Positive(message = "Customer Id must be greater than 0.")
	    private Long customerId;

}
