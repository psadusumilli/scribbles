package com.vijayrc.tasker.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class UnzipInterceptor implements ReaderInterceptor {
    private static Logger log  = LogManager.getLogger(UnzipInterceptor.class);

    @Override
    public Object aroundReadFrom(ReaderInterceptorContext context) throws IOException, WebApplicationException {
        context.setInputStream(new GZIPInputStream(context.getInputStream()));
        log.info("unzip|"+context.getMediaType());
        return context.proceed();
    }
}
