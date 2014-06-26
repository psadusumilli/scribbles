package com.vijayrc.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("tasks")
public class Task {

   @GET
   @Path("/explain")
   @Produces
   public String explain(){
      return "tasks..tasks..";
   }
}
