package com.todoapp.todoApp;

import com.todoapp.configuration.Config;
import com.todoapp.dao.TaskDAO;
import com.todoapp.resources.TaskResource;
import com.todoapp.tasks.CleanupTask; // Import the CleanupTask class
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import java.sql.Connection;
import java.sql.SQLException;

public class TodoApplication extends Application<Config> {

    public static void main(String[] args) throws Exception {
        new TodoApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<Config> bootstrap) {
        // Initialization logic if needed
    }

    @Override
    public void run(Config configuration, Environment environment) throws SQLException{
        // Access the database configuration
        String dbUrl = configuration.getDatabase().getUrl();
        String dbUser = configuration.getDatabase().getUser();
        String dbPassword = configuration.getDatabase().getPassword();

        try {
            // Get the database connection using the configuration values
            Connection connection = DatabaseConnection.getConnection(configuration);

            // Use the connection as needed
            System.out.println("Database connection established: " + (connection != null));

            // Don't forget to close the connection when done
            DatabaseConnection.closeConnection(connection);
        } catch (SQLException e) {
            // Handle the SQLException here
            System.err.println("Error establishing database connection: " + e.getMessage());
            e.printStackTrace();
        }

        // Initialize DAO and Resource
        TaskDAO taskDAO = new TaskDAO(configuration);
        TaskResource taskResource = new TaskResource(taskDAO);

        // Initialize the CleanupTask (this will run periodically)
        CleanupTask cleanupTask = new CleanupTask(taskDAO);

        // Register CleanupTask as a managed lifecycle component
        environment.lifecycle().manage(cleanupTask);

        // Register the TaskResource with Dropwizard's Jersey environment
        environment.jersey().register(taskResource);
    }
}
