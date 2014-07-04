package com.vijayrc.tasker.api;

import com.vijayrc.tasker.TaskParam;
import com.vijayrc.tasker.service.TaskService;
import com.vijayrc.tasker.view.TaskView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("tasks")
public class TaskApi {
    private static Logger log = LogManager.getLogger(TaskApi.class);

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
    @GET
    @Path("/filter/{field}")
    @Produces({"application/xml", "application/json"})
    public Response filter(@BeanParam TaskParam taskParam){
        log.info(taskParam);
        return Response.ok(service.getFor("1")).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public void delete(@PathParam("id") String id){
        service.remove(id);
    }

}
