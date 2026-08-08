package com.sbb.api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbb.api.dto.customer.CustomerRequest;
import com.sbb.api.dto.customer.CustomerResponse;
import com.sbb.api.dto.customer.CustomerUpdateRequest;
import com.sbb.api.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public CustomerResponse createCustomer(@Valid @RequestBody CustomerRequest request) {
        System.out.println("CustomerController.createCustomer()");
        return customerService.createCustomer(request);
    }

    @GetMapping("/{id}")
    public CustomerResponse getCustomerById(@PathVariable Long id) {
        System.out.println("CustomerController.getCustomerById()");
        return customerService.getCustomerById(id);
    }

    @GetMapping
    public List<CustomerResponse> getAllCustomers() {
        System.out.println("CustomerController.getAllCustomers()");
        return customerService.getAllCustomers();
    }

    @PutMapping("/{id}")
    public CustomerResponse updateCustomer(@PathVariable Long id,@Valid @RequestBody CustomerUpdateRequest request) {
        System.out.println("CustomerController.updateCustomer()");
        return customerService.updateCustomer(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        System.out.println("CustomerController.deleteCustomer()");
        customerService.deleteCustomer(id);
        return "Customer Deleted Successfully";
    }
}
