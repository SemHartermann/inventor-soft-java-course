package org.example.dao;

import org.example.entity.User;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    User addUser(User user) throws SQLException;

    User extractUser(ResultSet rs) throws SQLException;

    List<User> findUsers() throws SQLException;

    User findUserByEmail(String email) throws SQLException;

    User findUserById(Long id) throws SQLException;

    void updateUser(User user) throws SQLException;

    boolean deleteUserById(Long id) throws SQLException;
}