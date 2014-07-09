package com.vijayrc.tasker;

import com.vijayrc.tasker.filter.ServerFilter;
import com.vijayrc.tasker.interceptor.ServerWriteInterceptor;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class Tasker extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(MultiPartFeature.class);
        classes.add(LoggingFilter.class);
        classes.add(ServerFilter.class);
        classes.add(ServerWriteInterceptor.class);
        return classes;
    }
}
