package com.example.task.service;

import com.example.task.entity.User;
import com.example.task.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Setter
public class UserService {
    private UserRepository userRepository;
    public List<User> getAllCustomers() {
        return userRepository.findAll();
    }
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("Customer not found with login: " + email));
    }
    public User addCustomer(User user) {
        return userRepository.save(user);
    }
    public void deleteCustomerById(Long id) {
        userRepository.deleteById(id);
    }
}
