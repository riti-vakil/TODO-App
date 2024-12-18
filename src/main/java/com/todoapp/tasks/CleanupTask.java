package com.todoapp.tasks;

import com.todoapp.dao.TaskDAO;
import io.dropwizard.lifecycle.Managed;

import java.util.Timer;
import java.util.TimerTask;

public class CleanupTask implements Managed {

    private final TaskDAO taskDAO;
    private Timer timer;

    // Constructor to initialize DAO
    public CleanupTask(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    // This method is called when the app starts, setting up the periodic cleanup task
    @Override
    public void start() throws Exception {
        // Run cleanup every 24 hours
        timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Call the cleanup method
                cleanupOldTasks();
            }
        }, 0, 24 * 60 * 60 * 1000); // 24 hours in milliseconds
    }

    // This method is called when the app stops, canceling the timer
    @Override
    public void stop() throws Exception {
        if (timer != null) {
            timer.cancel();
        }
    }

    // Method to delete tasks that are marked as "DONE" and older than 6 months
    private void cleanupOldTasks() {
        try {
            System.out.println("Cleaning up old completed tasks...");
            int deletedRows = taskDAO.deleteOldCompletedTasks(); // Call DAO to delete old tasks
            System.out.println("Deleted " + deletedRows + " old tasks.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while cleaning up old tasks.");
        }
    }
}
