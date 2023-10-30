package org.example.service;

import org.example.dao.UserDao;
import org.example.dao.h2impl.UserDaoHTwoImpl;
import org.example.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    UserDao userDao = new UserDaoHTwoImpl();

    User addUser(User user) throws SQLException;

    List<User> findUsers() throws SQLException;

    User findUserByEmail(String email) throws SQLException;

    User findUserById(Long id) throws SQLException;

    User updateUser(User user) throws SQLException;

    boolean deleteUserById(Long id) throws SQLException;
}
