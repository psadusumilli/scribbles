package com.vijayrc.tasker.api;

import com.vijayrc.tasker.error.TaskNotFound;
import com.vijayrc.tasker.service.TaskService;
import com.vijayrc.tasker.view.TaskView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.net.ResponseCache;
import java.util.List;

import static javax.ws.rs.core.Response.Status.*;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.status;

@Component
public class TaskApi {
    private static Logger log = LogManager.getLogger(TaskApi.class);

    @Autowired
    private TaskService service;

    @GET
    @Produces({"application/xml", "application/json"})
    public Response allFor(@PathParam("card") String card){
        try {
            return ok(service.getFor(card)).build();
        } catch (TaskNotFound e) {
            log.warn("not found|"+card);
            return status(NOT_FOUND).build();
        }
    }
    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Response getFor(@PathParam("card") String card, @PathParam("id") String id){
        try {
            return ok(service.getFor(card, id)).build();
        } catch (TaskNotFound taskNotFound) {
            log.warn("not found|card="+card+"|id="+id);
            return status(NOT_FOUND).build();
        }
    }
}
