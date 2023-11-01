package org.example.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.DBManager;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DataLoader {
    private static final Logger LOG = LogManager.getLogger(DataLoader.class);

    private static Connection connection;

    static public void seedData() throws GeneralSecurityException, UnsupportedEncodingException {

        try {
            connection = DBManager.getInstance().getConnection();
            Statement statement = connection.createStatement();

            statement.execute("create table game (" + "        id bigint generated by default as identity," + "        genre varchar(255) not null," + "        name varchar(255) not null unique," + "        primary key (id)" + "    )");

            statement.execute("create table \"user\" (" + "        id bigint generated by default as identity," + "        email varchar(255) not null unique," + "        password varchar(255),\n" + "        role varchar(255) check (role in ('USER','ADMIN'))," + "        primary key (id)" + "    )");

            statement.execute("insert " + "    into" + "        game" + "        (genre,name,id) " + "    values" + "        ('shooter','cs2',default)");

            statement.execute("insert " + "    into" + "        game" + "        (genre,name,id) " + "    values" + "        ('moba','dota2',default)");

            PreparedStatement preparedStatement = connection.prepareStatement("insert " + "    into" + "        \"user\"" + "        (email,password,id) " + "    values" + "        (?,?,default)");

            preparedStatement.setString(1, "admin@gmail.com");
            preparedStatement.setString(2, PasswordEncoder.encode("admin"));

            preparedStatement.executeUpdate();

            preparedStatement.setString(1, "user@gmail.com");
            preparedStatement.setString(2, PasswordEncoder.encode("user"));

            preparedStatement.executeUpdate();
            statement.close();
            preparedStatement.close();

        } catch (SQLException e) {
            LOG.fatal(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection() throws SQLException {
        connection.close();
    }
}
