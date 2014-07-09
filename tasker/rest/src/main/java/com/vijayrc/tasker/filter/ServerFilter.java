package com.vijayrc.tasker.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import java.io.IOException;

public class ServerFilter implements ContainerRequestFilter{
    private static Logger log = LogManager.getLogger(ServerFilter.class);

    @Override
    public void filter(ContainerRequestContext context) throws IOException {
        log.debug("|-----------------------------------------------------------|");
    }
}