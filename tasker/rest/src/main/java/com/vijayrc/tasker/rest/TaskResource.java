package com.vijayrc.tasker.rest;

import com.vijayrc.tasker.domain.Task;
import com.vijayrc.tasker.repository.AllTasks;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("tasks")
public class TaskResource {

    private AllTasks allTasks = new AllTasks();

    @GET
    @Path("explain")
    @Produces("text/plain")
    public String explain(){
        return "resource to track all my tasks";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Task> all(){
        return allTasks.all();
    }

}
