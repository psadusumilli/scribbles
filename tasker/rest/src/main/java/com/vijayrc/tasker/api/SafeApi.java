package com.vijayrc.tasker.api;

import org.apache.shiro.SecurityUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/safe")
public class SafeApi {
    @GET
    public Response get() {
        String state;
        if (SecurityUtils.getSubject().hasRole("vip")) {
            state = "authorized";
        } else {
            state = "authenticated";
        }
        return Response.ok(state).build();
    }

}
