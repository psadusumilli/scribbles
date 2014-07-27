package com.vijayrc.tasker.api;

import com.vijayrc.tasker.error.TaskNotFound;
import com.vijayrc.tasker.error.TaskNotFoundWebError;
import com.vijayrc.tasker.service.TaskService;
import com.vijayrc.tasker.view.TaskView;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
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
@Api(value = "tasks",description = "task operations")
public class TaskApi {
    private static Logger log = LogManager.getLogger(TaskApi.class);

    @Autowired
    private TaskService service;

    @GET
    @Produces({"application/json"})
    @ApiOperation(value = "get tasks for given card",response = List.class)
    public Response allFor(@ApiParam(value = "card number",required = true) @PathParam("card") String card){
        try {
            return ok(service.getFor(card)).build();
        } catch (TaskNotFound e) {
            throw new TaskNotFoundWebError(card);
        }
    }
    @GET
    @Path("/{id}")
    @Produces({"application/json"})
    @ApiOperation(value = "get a task in the given card",response = TaskView.class)
    public Response getFor(@ApiParam(value = "card number",required = true) @PathParam("card") String card,
                           @ApiParam(value = "task id",required = true) @PathParam("id") String id){
        try {
            return ok(service.getFor(card, id)).build();
        } catch (TaskNotFound taskNotFound) {
            throw new TaskNotFoundWebError("card="+card+"|id="+id);
        }
    }
}
