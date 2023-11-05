package org.example.dao;

import jakarta.persistence.EntityManager;
import org.example.entity.User;

import java.util.List;

public interface UserDao {
    User addUser(User user);

    List<User> findUsers();

    User findUserByEmail(String email);

    User findUserById(Long id);

    void updateUser(User user);

    void deleteUser(User user);

    void injectEntityManager(EntityManager entityentityManager);
}