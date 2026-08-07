package com.sbb.api.dto.auth;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
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
public class CustomerRegisterRequest extends BaseRegisterRequest {

	    @NotBlank(message = "First Name is required.")
	    @Pattern(
	            regexp = "^[A-Za-z ]{2,50}$",
	            message = "First Name must contain only letters and be 2 to 50 characters long."
	    )
	    private String firstName;

	    @NotBlank(message = "Last Name is required.")
	    @Pattern(
	            regexp = "^[A-Za-z ]{2,50}$",
	            message = "Last Name must contain only letters and be 2 to 50 characters long."
	    )
	    private String lastName;


	    @NotBlank(message = "Address is required.")
	    private String address;

	    @NotBlank(message = "City is required.")
	    private String city;

	    @NotBlank(message = "State is required.")
	    private String state;

	    @NotBlank(message = "Pincode is required.")
	    @Pattern(
	            regexp = "^[1-9][0-9]{5}$",
	            message = "Pincode must be a valid 6-digit Indian pincode."
	    )
	    private String pincode;

	    @NotNull(message = "Date of Birth is required.")
	    @Past(message = "Date of Birth must be in the past.")
	    private LocalDate dateOfBirth;
	    
}
