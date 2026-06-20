package com.sbb.api.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sbb.api.dao.UserRepository;
import com.sbb.api.entity.User;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        System.out.println("UserService.createUser()");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        System.out.println("UserService.getUserById()");
        return userRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    public List<User> getAllUsers() {
        System.out.println("UserService.getAllUsers()");
        return userRepository.findAll();
    }

    public User updateUser(Long id, User user) {
        System.out.println("UserService.updateUser()");
        User existingUser = getUserById(id);
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setAddress(user.getAddress());
        existingUser.setCity(user.getCity());
        existingUser.setState(user.getState());
        existingUser.setPincode(user.getPincode());
        existingUser.setCustomerId(user.getCustomerId());
        existingUser.setDateOfBirth(user.getDateOfBirth());
        existingUser.setActive(user.getActive());
        existingUser.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        System.out.println("UserService.deleteUser()");
        User user = getUserById(id);
        userRepository.delete(user);
    }
}
