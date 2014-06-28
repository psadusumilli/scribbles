package com.vijayrc.tasker.rest;

import com.vijayrc.tasker.service.TaskService;
import com.vijayrc.tasker.view.TaskView;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("tasks")
public class TaskApi {
    private TaskService service = new TaskService();

    @GET
    @Path("explain")
    @Produces("text/plain")
    public String explain(){
        return "resource to track all my tasks";
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<TaskView> all(){
        return service.getAll();
    }

}
