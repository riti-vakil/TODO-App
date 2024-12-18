package com.todoapp.todoApp;

import com.todoapp.configuration.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Method to establish a connection to the database using configuration parameters
    public static Connection getConnection(Config configuration) throws SQLException {
        // Get the database connection details from configuration
        String url = configuration.getDatabase().getUrl();
        String user = configuration.getDatabase().getUser();
        String password = configuration.getDatabase().getPassword();

        try {
            // Establish connection to the database using the provided details
            Connection connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (SQLException e) {
            // Log and throw the exception in case of a connection failure
            throw new SQLException("Failed to connect to the database", e);
        }
    }

    // Optionally, you can add a method to close the connection (if needed in your application)
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace(); // Handle exception if needed
            }
        }
    }
}
