package org.example;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private static final Logger LOG = Logger.getLogger(DBManager.class);
    private static final String URL = "jdbc:h2:mem:memDB";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    private static DBManager instance;

    private DBManager() {}

    synchronized public static DBManager getInstance(){
        if(instance==null){
            instance = new DBManager();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        Connection con;

        try {
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        }catch (SQLException e) {
            LOG.fatal("Unable to get connection");
            throw new SQLException(e);
        }

        return con;
    }
}