package com.vijayrc.tasker.api;

import com.vijayrc.tasker.filter.Track;
import com.vijayrc.tasker.service.SearchService;
import com.vijayrc.tasker.view.SearchView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.CompletionCallback;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Response;

import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;

@Component
@Path("search")
@Track
public class SearchApi {
    private static Logger log = LogManager.getLogger(SearchApi.class);

    @Autowired
    private SearchService service;

    @GET
    public void fetchFor(@MatrixParam("key") String key, @Suspended final AsyncResponse response){
        log.info("search|"+key);
        response.setTimeoutHandler(r ->  r.resume(Response.status(SERVICE_UNAVAILABLE).entity("search timed out.").build()));
        response.setTimeout(20, SECONDS);
        response.register((CompletionCallback) error -> {
            if( error != null) log.error("search error:|key="+key+"|error="+error);
            else log.info("search complete:|key="+key);
        });
        new Thread(()->{
            List<SearchView> views = service.fetch(key);
            response.resume(views);
        }).start();
    }
}
