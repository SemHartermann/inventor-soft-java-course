package org.example.dao.h2impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.DBManager;
import org.example.dao.UserDao;
import org.example.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHTwoImpl implements UserDao {
    private static final Logger LOG = LogManager.getLogger(UserDaoHTwoImpl.class);

    private static final String SQL_ADD_NEW_USER = "INSERT INTO \"user\" (email, password, id) VALUES (?, ?, default)";
    private static final String SQL_FIND_ALL_USERS = "SELECT * FROM \"user\"";
    private static final String SQL_FIND_USER_BY_EMAIL = "SELECT * FROM \"user\" WHERE email=?";
    private static final String SQL_FIND_USER_BY_ID = "SELECT * FROM \"user\" WHERE id=?";
    private static final String SQL_UPDATE_USER = "UPDATE \"user\" SET (email, password) = (?, ?) WHERE id=?";
    private static final String SQL_DELETE_USER_BY_ID = "DELETE FROM \"user\" WHERE id=?";


    public boolean deleteUserById(Long id) throws SQLException {
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQL_DELETE_USER_BY_ID)) {
            preparedStatement.setLong(1, id);
            int count = preparedStatement.executeUpdate();
            if (count < 1) {
                return false;
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new SQLException(ex);
        }
        return true;
    }

    public User addUser(User user) throws SQLException {
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQL_ADD_NEW_USER)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new SQLException(ex);
        }
        return user;
    }

    public User extractUser(ResultSet rs) throws SQLException {
        User user;

        try {
            user = User
                    .builder()
                    .id(rs.getLong("id"))
                    .email(rs.getString("email"))
                    .password(rs.getString("password"))
                    .build();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new SQLException(ex);
        }
        return user;
    }

    public List<User> findUsers() throws SQLException {
        List<User> UserList = new ArrayList<>();

        try (Connection con = DBManager.getInstance().getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_FIND_ALL_USERS)) {
            while (rs.next()) {
                UserList.add(extractUser(rs));
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new SQLException(ex);
        }
        return UserList;
    }

    public User findUserByEmail(String email) throws SQLException {
        User User = null;

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQL_FIND_USER_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                User = extractUser(rs);
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new SQLException(ex);
        }

        return User;
    }

    @Override
    public User findUserById(Long id) throws SQLException {
        User user = null;

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQL_FIND_USER_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                user = extractUser(rs);
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new SQLException(ex);
        }

        return user;
    }

    public void updateUser(User user) throws SQLException {
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQL_UPDATE_USER)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setLong(3, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new SQLException(ex);
        }
    }
}
