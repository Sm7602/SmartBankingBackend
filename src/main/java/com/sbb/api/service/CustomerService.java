package com.sbb.api.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sbb.api.dao.CustomerRepository;
import com.sbb.api.dto.customer.CustomerRequest;
import com.sbb.api.dto.customer.CustomerResponse;
import com.sbb.api.dto.customer.CustomerUpdateRequest;
import com.sbb.api.entity.Customer;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    
    private CustomerResponse convertToResponse(Customer customer) {

        return CustomerResponse.builder()
        		   .id(customer.getId())
        		   .firstName(customer.getFirstName())
 	            .lastName(customer.getLastName())
                 .phoneNumber(customer.getPhoneNumber())
                 .address(customer.getAddress())
                 .city(customer.getCity())
                 .state(customer.getState())
                 .pincode(customer.getPincode())
                 .dateOfBirth(customer.getDateOfBirth())
                 .createdAt(customer.getCreatedAt())
                 .updatedAt(customer.getUpdatedAt())
                 .active(true)
                 .accounts(customer.getAccounts())
                 .beneficiaries(customer.getBeneficiaries())
                 .scheduledPayments(customer.getScheduledPayments())
                 .wallet(customer.getWallet())
                 .user(customer.getUser())
                 .build();

    }

    public CustomerResponse createCustomer(CustomerRequest request) {
        System.out.println("UserService.createCustomer()");


        Customer customer=Customer.builder()
        		    .firstName(request.getFirstName())
 	            .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .city(request.getCity())
                .state(request.getState())
                .pincode(request.getPincode())
                .dateOfBirth(request.getDateOfBirth())
                .updatedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .active(true)
                .build();


        customer=customerRepository.save(customer);
        return convertToResponse(customer);
    }

    public CustomerResponse getCustomerById(Long id) {
        System.out.println("UserService.getCustomerById()");
        Customer customer= customerRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Customer not found"));
        
        return convertToResponse(customer);
    }

    public List<CustomerResponse> getAllCustomers() {
        System.out.println("UserService.getAllCustomers()");
        return customerRepository.findAll()
                .stream()
	            .map(this::convertToResponse)
	            .toList();
    }

    public CustomerResponse updateCustomer(Long id, CustomerUpdateRequest request) {
        System.out.println("UserService.updateCustomer()");
        Customer existingUser =customerRepository.findById(id).orElseThrow(() ->
        new RuntimeException("Customer not found"));
        
        existingUser.setFirstName(request.getFirstName());
        existingUser.setLastName(request.getLastName());
        existingUser.setPhoneNumber(request.getPhoneNumber());
        existingUser.setAddress(request.getAddress());
        existingUser.setCity(request.getCity());
        existingUser.setState(request.getState());
        existingUser.setPincode(request.getPincode());
        existingUser.setDateOfBirth(request.getDateOfBirth());
        existingUser.setActive(request.getActive());
        existingUser.setUpdatedAt(LocalDateTime.now());

        existingUser=customerRepository.save(existingUser);
        return convertToResponse(existingUser);
    }

    public void deleteCustomer(Long id) {
        System.out.println("UserService.deleteCustomer()");
        Customer customer = customerRepository.findById(id).orElseThrow(() ->
        new RuntimeException("Customer not found"));
        customerRepository.delete(customer);
    }
}
