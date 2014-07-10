package com.vijayrc.tasker.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;

@Track
public class ServerResponseFilter implements ContainerResponseFilter {
    private static Logger log = LogManager.getLogger(ServerResponseFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        log.debug(requestContext.getMethod()+"|"+responseContext.getStatus());
        log.debug("---------------------------------------------------------------------------------------------");
    }
}
