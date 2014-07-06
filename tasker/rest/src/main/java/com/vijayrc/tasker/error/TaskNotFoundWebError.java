package com.vijayrc.tasker.error;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class TaskNotFoundWebError extends WebApplicationException {
    private static Logger log = LogManager.getLogger(TaskNotFoundWebError.class);

    public TaskNotFoundWebError(String message) {
        super(Response.status(Response.Status.NOT_FOUND)
                .type("text/plain")
                .entity("task not found|" + message)
                .build());
        log.warn("task not found|" + message);
    }
}
