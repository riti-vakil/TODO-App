package com.todoapp.models;

import java.time.LocalDate;

public class Task {

    private int id;
    private String description;
    private String status;
    private LocalDate startDate;
    private LocalDate targetDate;

    // Constructor
    public Task(int id, String description, String status, LocalDate startDate, LocalDate targetDate) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.startDate = startDate;
        this.targetDate = targetDate;
    }

    // Default constructor (optional, useful for ORM frameworks like Hibernate)
    public Task() {
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", startDate=" + startDate +
                ", targetDate=" + targetDate +
                '}';
    }
}
