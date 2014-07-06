package com.vijayrc.tasker.error;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class WebError extends WebApplicationException {
    public WebError(Exception e) {
        super(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build());
    }
}
