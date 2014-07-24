package com.vijayrc.security.api;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Component
@Path("/login")
public class LoginApi {

    @GET
    public String all(){
       return "all samples will be listed";
    }
}
