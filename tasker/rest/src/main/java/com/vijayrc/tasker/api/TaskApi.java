package com.vijayrc.tasker.api;

import com.vijayrc.tasker.service.TaskService;
import com.vijayrc.tasker.view.TaskView;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Component
@Path("tasks")
@Log4j2
public class TaskApi {
    @Autowired
    private TaskService service;

    @GET
    @Path("explain")
    @Produces("text/plain")
    public String explain(){
        return "resource to track all my tasks";
    }
    @GET
    @Produces({"application/xml", "application/json"})
    public List<TaskView> all(){
        return service.getAll();
    }
    @GET
    @Path("/{id}")
    @Produces({"application/xml", "application/json"})
    public TaskView get(@PathParam("id") String id){
       return service.getFor(id);
    }
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public void delete(@PathParam("id") String id){
        service.remove(id);
    }

}
