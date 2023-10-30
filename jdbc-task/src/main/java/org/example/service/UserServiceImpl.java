package org.example.service;

import org.example.entity.User;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    @Override
    public User addUser(User user) throws SQLException {
        return userDao.addUser(user);
    }

    @Override
    public List<User> findUsers() throws SQLException {
        return userDao.findUsers();
    }

    @Override
    public User findUserByEmail(String email) throws SQLException {
        return userDao.findUserByEmail(email);
    }

    @Override
    public User findUserById(Long id) throws SQLException {
        return userDao.findUserById(id);
    }

    @Override
    public User updateUser(User user) throws SQLException {
        userDao.updateUser(user);

        return user;
    }

    @Override
    public boolean deleteUserById(Long id) throws SQLException {
        return userDao.deleteUserById(id);
    }
}
