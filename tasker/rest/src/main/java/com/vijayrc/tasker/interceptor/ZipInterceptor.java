package com.vijayrc.tasker.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class ZipInterceptor implements WriterInterceptor {
    private static Logger log = LogManager.getLogger(ZipInterceptor.class);

    @Override
    public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
        System.out.println(context.getHeaders());
        System.out.println(context.getPropertyNames());
        context.setOutputStream(new GZIPOutputStream(context.getOutputStream()));
        log.info("gzip|" + context.getEntity());
        context.proceed();
    }
}
