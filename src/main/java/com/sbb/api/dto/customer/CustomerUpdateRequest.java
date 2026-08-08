package com.sbb.api.dto.customer;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CustomerUpdateRequest {

	private String firstName;

    private String lastName;

    private String phoneNumber;

    private String address;

    private String city;

    private String state;

    private String pincode;

    private LocalDate dateOfBirth;
    
    private Boolean active;
	
	
}
