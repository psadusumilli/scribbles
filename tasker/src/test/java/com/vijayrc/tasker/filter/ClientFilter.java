package com.vijayrc.tasker.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import java.io.IOException;

public class ClientFilter implements ClientRequestFilter {
    private static Logger log = LogManager.getLogger(ClientFilter.class);

    @Override
    public void filter(ClientRequestContext context) throws IOException {
        context.getHeaders().add("zip","yes");
        log.info("add header|gzip|" + context.getHeaders());
    }
}
