package com.todoapp.dao;

import com.todoapp.configuration.Config;
import com.todoapp.models.Task;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static com.todoapp.todoApp.DatabaseConnection.getConnection;

public class TaskDAO {

    private final Config configuration;
    public TaskDAO(Config configuration) {
        this.configuration = configuration;
    }

    // Get all tasks
    public List<Task> getAllTasks() throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";

        try (Connection connection = getConnection(configuration);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Task task = new Task(
                        resultSet.getInt("id"),
                        resultSet.getString("description"),
                        resultSet.getString("status"),
                        resultSet.getDate("start_date").toLocalDate(),
                        resultSet.getDate("target_date").toLocalDate()
                );
                tasks.add(task);
            }
        }
        return tasks;
    }

    // Get a task by ID
    public Task getTaskById(int id) throws SQLException {
        String sql = "SELECT * FROM tasks WHERE id = ?";
        try (Connection connection = getConnection(configuration);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Task(
                        resultSet.getInt("id"),
                        resultSet.getString("description"),
                        resultSet.getString("status"),
                        resultSet.getDate("start_date").toLocalDate(),
                        resultSet.getDate("target_date").toLocalDate()
                );
            }
        }
        return null;
    }

    // Create a task
    public void createTask(Task task) throws SQLException {
        String sql = "INSERT INTO tasks (description, status, start_date, target_date) VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnection(configuration);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, task.getDescription());
            statement.setString(2, task.getStatus());
            statement.setDate(3, Date.valueOf(task.getStartDate()));
            statement.setDate(4, Date.valueOf(task.getTargetDate()));
            statement.executeUpdate();
        }
    }

    // Update a task
    public boolean updateTask(Task task) throws SQLException {
        String sql = "UPDATE tasks SET description = ?, status = ?, start_date = ?, target_date = ? WHERE id = ?";
        try (Connection connection = getConnection(configuration);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, task.getDescription());
            statement.setString(2, task.getStatus());
            statement.setDate(3, Date.valueOf(task.getStartDate()));
            statement.setDate(4, Date.valueOf(task.getTargetDate()));
            statement.setInt(5, task.getId());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    // Delete a task
    public boolean deleteTask(int id) throws SQLException {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (Connection connection = getConnection(configuration);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        }
    }
    // Remove tasks from db after 6 months if status is DONE
    public int deleteOldCompletedTasks() {
        String sql = "DELETE FROM tasks WHERE status = 'DONE' AND target_date < NOW() - INTERVAL 6 MONTH";
        try (Connection connection = getConnection(configuration);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to delete old completed tasks", e);
        }
    }
}
