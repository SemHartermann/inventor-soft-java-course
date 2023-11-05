package org.example.service;

import org.example.entity.User;

import java.util.List;

public interface UserService {
    User addUser(User user);

    List<User> findUsers();

    User findUserByEmail(String email);

    User findUserById(Long id);

    User updateUser(User user);

    void deleteUserById(Long id);
}
