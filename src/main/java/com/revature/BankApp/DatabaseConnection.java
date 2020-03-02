package com.revature.BankApp;

import java.sql.*;

public class DatabaseConnection {
	

    private static DatabaseConnection instance;
    private Connection connection;
    private String url = "jdbc:oracle:thin:@localhost:1521:ORCL";
    private String username = "vikki";
    private String password = "vikki";
    private DatabaseConnection() throws SQLException {
        
           // Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
        
    }
    public Connection getConnection() {
        return connection;
    }
    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnection();
        } else if (instance.getConnection().isClosed()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
}