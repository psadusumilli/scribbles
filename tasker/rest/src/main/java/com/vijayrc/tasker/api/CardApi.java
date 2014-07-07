package com.vijayrc.tasker.api;

import com.vijayrc.tasker.error.CardNotFound;
import com.vijayrc.tasker.error.CardNotFoundWebError;
import com.vijayrc.tasker.error.WebError;
import com.vijayrc.tasker.param.CardParam;
import com.vijayrc.tasker.service.CardService;
import com.vijayrc.tasker.view.CardView;
import com.vijayrc.tasker.view.TaskView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status.*;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.serverError;
import static javax.ws.rs.core.Response.status;

@Component
@Path("cards")
public class CardApi {
    private static Logger log = LogManager.getLogger(CardApi.class);

    @Autowired
    private CardService service;
    @Autowired
    private TaskApi taskApi;

    @GET
    @Produces({"application/json"})
    public List<CardView> all(){
        return service.getAll();
    }
    @GET
    @Path("/{id}")
    @Produces({"application/json"})
    public Response get(@PathParam("id") String id){
        try {
            return ok(service.getFor(id)).build();
        } catch (CardNotFound e) {
            throw new CardNotFoundWebError(id);
        }
    }
    @GET
    @Path("/filter/{field}")
    @Produces({"application/xml", "application/json"})
    public Response filter(@BeanParam CardParam cardParam){
        log.info(cardParam);
        try {
            return ok(service.getFor("1")).build();
        } catch (CardNotFound e) {
            throw new CardNotFoundWebError("1");
        }
    }
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id){
        service.remove(id);
        return status(NO_CONTENT).build();
    }
    @POST
    @Produces({"application/json"})
    @Consumes("application/json")
    public Response create(CardView cardView){
        try {
            log.info("received|"+cardView);
            return ok(service.create(cardView)).build();
        } catch (Exception e) {
            log.error(e);
            throw new WebError(e);
        }
    }
    @PUT
    @Produces({"application/json"})
    @Consumes("application/json")
    public Response update(CardView cardView){
        try {
            log.info("received|"+cardView);
            return ok(service.update(cardView)).build();
        } catch (CardNotFound e) {
            throw new CardNotFoundWebError(cardView.getId());
        } catch (Exception e) {
            log.error(e);
            throw new WebError(e);
        }
    }
    @Path("{card}/tasks")
    public TaskApi task(){
        return taskApi;
    }

}
