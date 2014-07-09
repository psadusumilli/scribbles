package com.vijayrc.tasker.config;

import com.vijayrc.tasker.filter.ClientFilter;
import com.vijayrc.tasker.interceptor.ClientReadInterceptor;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class TestMaker {
    public static String baseUrl() {
        return "http://localhost:9090/tasker/rest/";
    }
    public static Client client(){
        return ClientBuilder.newClient()
                .register(ClientFilter.class)
                .register(ClientReadInterceptor.class)
                .register(MultiPartFeature.class);
    }
}
