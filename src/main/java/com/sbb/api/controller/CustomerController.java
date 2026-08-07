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
import com.sbb.api.entity.Customer;
import com.sbb.api.service.CustomerService;

@RestController
@RequestMapping("/api/users")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public Customer createUser(@RequestBody Customer customer) {
        System.out.println("CustomerController.createUser()");
        return customerService.createUser(customer);
    }

    @GetMapping("/{id}")
    public Customer getUserById(@PathVariable Long id) {
        System.out.println("CustomerController.getUserById()");
        return customerService.getUserById(id);
    }

    @GetMapping
    public List<Customer> getAllUsers() {
        System.out.println("CustomerController.getAllUsers()");
        return customerService.getAllUsers();
    }

    @PutMapping("/{id}")
    public Customer updateUser(@PathVariable Long id,@RequestBody Customer customer) {
        System.out.println("CustomerController.updateUser()");
        return customerService.updateUser(id, customer);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        System.out.println("CustomerController.deleteUser()");
        customerService.deleteUser(id);
        return "Customer Deleted Successfully";
    }
}
