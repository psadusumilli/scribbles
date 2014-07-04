package com.vijayrc.tasker.api;

import com.vijayrc.tasker.service.TaskService;
import com.vijayrc.tasker.view.TaskView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Component
public class TaskApi {
    @Autowired
    private TaskService service;

    @GET
    @Produces({"application/xml", "application/json"})
    public List<TaskView> allFor(@PathParam("card") String card){
        return service.getFor(card);
    }
    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public TaskView getFor(@PathParam("card") String card,@PathParam("card") String id){
        return service.getFor(card,id);
    }
}
