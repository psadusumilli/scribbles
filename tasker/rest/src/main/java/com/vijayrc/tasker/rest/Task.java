package com.vijayrc.tasker.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("tasks")
public class Task {

    @GET
    @Produces("text/plain")
    public String explain(){
        return "all my tasks";
    }
}
