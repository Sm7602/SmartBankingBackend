package com.sbb.api.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sbb.api.dao.CustomerRepository;
import com.sbb.api.entity.Customer;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer createUser(Customer customer) {
        System.out.println("UserService.createUser()");
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        return customerRepository.save(customer);
    }

    public Customer getUserById(Long id) {
        System.out.println("UserService.getUserById()");
        return customerRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Customer not found"));
    }

    public List<Customer> getAllUsers() {
        System.out.println("UserService.getAllUsers()");
        return customerRepository.findAll();
    }

    public Customer updateUser(Long id, Customer customer) {
        System.out.println("UserService.updateUser()");
        Customer existingUser = getUserById(id);
        existingUser.setFirstName(customer.getFirstName());
        existingUser.setLastName(customer.getLastName());
        existingUser.setPhoneNumber(customer.getPhoneNumber());
        existingUser.setAddress(customer.getAddress());
        existingUser.setCity(customer.getCity());
        existingUser.setState(customer.getState());
        existingUser.setPincode(customer.getPincode());
        existingUser.setDateOfBirth(customer.getDateOfBirth());
        existingUser.setActive(customer.getActive());
        existingUser.setUpdatedAt(LocalDateTime.now());

        return customerRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        System.out.println("UserService.deleteUser()");
        Customer customer = getUserById(id);
        customerRepository.delete(customer);
    }
}
