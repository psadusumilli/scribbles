package com.vijayrc.tasker.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

import static org.apache.commons.lang.StringUtils.equalsIgnoreCase;

public class ClientReadInterceptor implements ReaderInterceptor {
    private static Logger log  = LogManager.getLogger(ClientReadInterceptor.class);

    @Override
    public Object aroundReadFrom(ReaderInterceptorContext context) throws IOException, WebApplicationException {
        String zip = context.getHeaders().getFirst("zip");
        if(equalsIgnoreCase(zip, "yes")){
            context.setInputStream(new GZIPInputStream(context.getInputStream()));
            log.debug("unzip|" + context.getMediaType());
        }
        return context.proceed();
    }
}
