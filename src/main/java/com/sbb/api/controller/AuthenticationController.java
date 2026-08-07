package com.sbb.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbb.api.dto.auth.AdminRegisterRequest;
import com.sbb.api.dto.auth.AuthenticationResponse;
import com.sbb.api.dto.auth.BeneficiaryRegisterRequest;
import com.sbb.api.dto.auth.CustomerRegisterRequest;
import com.sbb.api.dto.auth.LoginRequest;
import com.sbb.api.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationService service;

	@PostMapping("/registerCustomer")
	public ResponseEntity<AuthenticationResponse> registerCustomer(
			@RequestBody CustomerRegisterRequest request){
		System.out.println("AuthgenticationController.registerCustomer()");
		return ResponseEntity.ok(service.registerCustomer(request));
	}
	
	@PostMapping("/registerAdmin")
	public ResponseEntity<AuthenticationResponse> registerAdmin(
			@RequestBody AdminRegisterRequest request){
		System.out.println("AuthgenticationController.registerAdmin()");
		return ResponseEntity.ok(service.registerAdmin(request));
	}
	
	@PostMapping("/registerBeneficiary")
	public ResponseEntity<AuthenticationResponse> registerBeneficiary(
			@RequestBody BeneficiaryRegisterRequest request){
		System.out.println("AuthgenticationController.registerBeneficiary()");
		return ResponseEntity.ok(service.registerBeneficiary(request));
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(
			@RequestBody LoginRequest request){
		System.out.println("AuthgenticationController login.....");
		return ResponseEntity.ok(service.authenticate(request));
	}
}

