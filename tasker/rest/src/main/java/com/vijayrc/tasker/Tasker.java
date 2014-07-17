package com.vijayrc.tasker;

import com.vijayrc.tasker.filter.ServerRequestFilter;
import com.vijayrc.tasker.filter.ServerResponseFilter;
import com.vijayrc.tasker.interceptor.ServerWriteInterceptor;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.sse.SseFeature;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class Tasker extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(MultiPartFeature.class);
        classes.add(LoggingFilter.class);
        classes.add(ServerRequestFilter.class);
        classes.add(ServerResponseFilter.class);
        classes.add(ServerWriteInterceptor.class);
        classes.add(SseFeature.class);
        return classes;
    }
}
