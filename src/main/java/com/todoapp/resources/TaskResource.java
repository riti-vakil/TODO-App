package com.todoapp.resources;

import com.todoapp.dao.TaskDAO;
import com.todoapp.models.Task;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskResource {

    private final TaskDAO taskDAO;

    // Constructor to initialize TaskDAO
    public TaskResource(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    // Get all tasks
    @GET
    public Response getAllTasks() {
        try {
            List<Task> tasks = taskDAO.getAllTasks();
            return Response.ok(tasks).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error fetching tasks").build();
        }
    }

    // Get a specific task by ID
    @GET
    @Path("/{id}")
    public Response getTaskById(@PathParam("id") int id) {
        try {
            Task task = taskDAO.getTaskById(id);
            if (task == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Task not found").build();
            }
            return Response.ok(task).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error fetching task").build();
        }
    }

    // Create a new task
    @POST
    public Response createTask(Task task) {
        try {
            taskDAO.createTask(task);
            return Response.status(Response.Status.CREATED).entity(task).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error creating task").build();
        }
    }

    // Update an existing task
    @PUT
    @Path("/{id}")
    public Response updateTask(@PathParam("id") int id, Task task) {
        try {
            task.setId(id);  // Ensure the task ID is set correctly
            boolean updated = taskDAO.updateTask(task);
            if (!updated) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Task not found").build();
            }
            return Response.ok(task).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error updating task").build();
        }
    }

    // Delete a task
    @DELETE
    @Path("/{id}")
    public Response deleteTask(@PathParam("id") int id) {
        try {
            boolean deleted = taskDAO.deleteTask(id);
            if (!deleted) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Task not found").build();
            }
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting task").build();
        }
    }
    @DELETE
    @Path("/cleanup")
    public Response cleanupCompletedTasks() {
        int rowsDeleted = taskDAO.deleteOldCompletedTasks();
        return Response.ok("Deleted " + rowsDeleted + " old completed tasks.").build();
    }

}
