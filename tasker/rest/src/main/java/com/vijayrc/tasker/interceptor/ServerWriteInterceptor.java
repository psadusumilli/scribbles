package com.vijayrc.tasker.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import static org.apache.commons.lang.StringUtils.equalsIgnoreCase;

public class ServerWriteInterceptor implements WriterInterceptor {
    private static Logger log = LogManager.getLogger(ServerWriteInterceptor.class);

    @Override
    public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
        String zip = (String) context.getHeaders().getFirst("zip");
        if(equalsIgnoreCase(zip, "yes")){
            context.setOutputStream(new GZIPOutputStream(context.getOutputStream()));
            log.debug("gzip|" + context.getEntity());
        }
        context.proceed();
    }
}
