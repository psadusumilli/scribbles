package com.vijayrc.tasker.error;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class CardNotFoundWebError extends WebApplicationException {
    private static Logger log = LogManager.getLogger(CardNotFoundWebError.class);

    public CardNotFoundWebError(String message) {
        super(Response.status(Response.Status.NOT_FOUND)
                .type("text/plain")
                .entity("card not found|" + message)
                .build());
        log.warn("card not found|" + message);
    }
}
