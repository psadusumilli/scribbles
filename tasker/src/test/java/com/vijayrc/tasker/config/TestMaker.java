package com.vijayrc.tasker.config;

import com.vijayrc.tasker.filter.ClientFilter;
import com.vijayrc.tasker.interceptor.ClientReadInterceptor;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.sse.SseFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class TestMaker {
    public static String baseUrl() {
        return "http://localhost:8080/tasker/rest/";
    }
    public static Client client(){
        return ClientBuilder.newClient()
                .register(ClientFilter.class)
                .register(ClientReadInterceptor.class)
                .register(SseFeature.class)
                .register(MultiPartFeature.class);
    }

    public static WebTarget target(){
        return client().target(baseUrl());
    }
}
