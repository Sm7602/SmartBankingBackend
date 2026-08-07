package com.sbb.api.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sbb.api.dao.AdminRepository;
import com.sbb.api.dao.BeneficiaryRepository;
import com.sbb.api.dao.CustomerRepository;
import com.sbb.api.dao.UserRepository;
import com.sbb.api.dto.auth.AdminRegisterRequest;
import com.sbb.api.dto.auth.AuthenticationResponse;
import com.sbb.api.dto.auth.BeneficiaryRegisterRequest;
import com.sbb.api.dto.auth.CustomerRegisterRequest;
import com.sbb.api.dto.auth.LoginRequest;
import com.sbb.api.entity.Admin;
import com.sbb.api.entity.Beneficiary;
import com.sbb.api.entity.Customer;
import com.sbb.api.entity.Role;
import com.sbb.api.entity.User;
import com.sbb.api.security.JwtService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import lombok.RequiredArgsConstructor;
import lombok.var;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final UserRepository userRepository;
	
	private final CustomerRepository customerRepository;
	
	private final BeneficiaryRepository beneficiaryRepository ;
	
	private final AdminRepository adminRepository;

	private final PasswordEncoder passwordEncoder;
	
	private final JwtService jwtService;
	
	private final AuthenticationManager authenticationManager;
	
	public AuthenticationResponse registerCustomer(CustomerRegisterRequest request) {
         System.out.println("AuthenticationService.registerCustomer()");
        
		 if (userRepository.findByEmail(request.getEmail()).isPresent()) {
		        throw new RuntimeException("Email is already registered. Please login.");
		    }
		
	    User user = User.builder()
	            .firstname(request.getFirstName())
	            .lastname(request.getLastName())
	            .email(request.getEmail())
	            .password(passwordEncoder.encode(request.getPassword()))
	            .role(Role.CUSTOMER)
	            .build();

	    user = userRepository.save(user);

	    Customer customer = Customer.builder()
	    		    .firstName(request.getFirstName())
	            .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .city(request.getCity())
                .state(request.getState())
                .pincode(request.getPincode())
                .dateOfBirth(request.getDateOfBirth())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .active(true)
                .user(user)
                .build();

	    customerRepository.save(customer);

	    String jwtToken = jwtService.generateToken(new HashMap<>(), user);

	    return AuthenticationResponse.builder()
	    		    .token(jwtToken)
	    	        .tokenType("Bearer")
	    	        .userId(user.getId())
	    	        .email(user.getEmail())
	    	        .role(user.getRole().name())
	    	        .message("Registration successful")
	    	        .build();
	}
	
	public AuthenticationResponse registerAdmin(AdminRegisterRequest request) {

         System.out.println("AuthenticationService.registerAdmin()");

		 if (userRepository.findByEmail(request.getEmail()).isPresent()) {
		        throw new RuntimeException("Email is already registered. Please login.");
		    }
		
	    User user = User.builder()
	    		    .firstname(request.getFirstName())
	            .lastname(request.getLastName())
	            .email(request.getEmail())
	            .password(passwordEncoder.encode(request.getPassword()))
	            .role(Role.ADMIN)
	            .build();

	    user = userRepository.save(user);

	    Admin admin = Admin.builder()
	    		    .firstName(request.getFirstName())
	            .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .active(true)
                .user(user)
	            .build();

	    adminRepository.save(admin);

	    String jwtToken = jwtService.generateToken(new HashMap<>(), user);

	    return AuthenticationResponse.builder()
    		    .token(jwtToken)
    	        .tokenType("Bearer")
    	        .userId(user.getId())
    	        .email(user.getEmail())
    	        .role(user.getRole().name())
    	        .message("Registration successful")
    	        .build();
	}
	
	
	public AuthenticationResponse registerBeneficiary(BeneficiaryRegisterRequest request) {

		 System.out.println("AuthenticationService.registerBeneficiary()");
		 
		 if (userRepository.findByEmail(request.getEmail()).isPresent()) {
		        throw new RuntimeException("Email is already registered. Please login.");
		    }
		 
		 Customer customer=customerRepository.findById(request.getCustomerId()).orElseThrow(() ->
         new RuntimeException("Customer not found"));
		
	    User user = User.builder()
	            .firstname(request.getBeneficiaryName())
	            .lastname("")
	            .email(request.getEmail())
	            .password(passwordEncoder.encode(request.getPassword()))
	            .role(Role.BENEFICIARY)
	            .build();

	    user = userRepository.save(user);

	    Beneficiary beneficiary = Beneficiary.builder()
	    		    .beneficiaryName(request.getBeneficiaryName())
	    		    .accountNumber(request.getAccountNumber())
	    		    .bankName(request.getBankName())
	    		    .ifscCode(request.getIfscCode())
	    		    .nickname(request.getNickname())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .active(true)
                .customer(customer)
                .user(user)
	            .build();

	    beneficiaryRepository.save(beneficiary);

	    String jwtToken = jwtService.generateToken(new HashMap<>(), user);

	    return AuthenticationResponse.builder()
    		    .token(jwtToken)
    	        .tokenType("Bearer")
    	        .userId(user.getId())
    	        .email(user.getEmail())
    	        .role(user.getRole().name())
    	        .message("Registration successful")
    	        .build();
	}
	
	public AuthenticationResponse authenticate(LoginRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail()
						,request.getPassword()));
		
		var user =userRepository.findByEmail(request.getEmail())
				.orElseThrow();
		var jwtToken =jwtService.generaTetoken(user);
		
		return AuthenticationResponse.builder()
    		    .token(jwtToken)
    	        .tokenType("Bearer")
    	        .userId(user.getId())
    	        .email(user.getEmail())
    	        .role(user.getRole().name())
    	        .message("Login successful")
    	        .build();
	}

}

