package com.vijayrc.tasker.api;

import com.vijayrc.tasker.error.CardNotFound;
import com.vijayrc.tasker.error.CardNotFoundWebError;
import com.vijayrc.tasker.error.WebError;
import com.vijayrc.tasker.param.CardParam;
import com.vijayrc.tasker.service.CardService;
import com.vijayrc.tasker.view.CardView;
import com.vijayrc.tasker.view.TaskView;
import com.wordnik.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.net.URI;
import java.util.List;

import static javax.ws.rs.core.Response.Status.*;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.serverError;
import static javax.ws.rs.core.Response.status;
import static org.apache.commons.lang.StringUtils.equalsIgnoreCase;

@Component
@Path("cards")
@Api(value = "cards", description = "Operations about cards")
public class CardApi {
    private static Logger log = LogManager.getLogger(CardApi.class);

    @Autowired
    private CardService service;
    @Autowired
    private TaskApi taskApi;
    @Context
    private UriInfo uriInfo;

    @GET
    @Produces({"application/json"})
    @ApiOperation(value = "find all cards", notes = "returns all cards in repository", response = CardView.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "cards bombed"),
            @ApiResponse(code = 404, message = "cards not found")
    })
    public List<CardView> all(){
        return service.getAll();
    }

    /** Contrived,
     *  1) client filter add custom header 'zip'
     *  2) server card api checks for 'zip' in request adds custom header 'zip' to response
     *  3) server interceptor does gzip compression based on 'zip'
     *  4) client interceptor does gzip expansion based on 'zip'
     *  5) also returns a link to card's tasks
     *  */
    @GET
    @Path("/{id}")
    @Produces({"application/json"})
    @ApiOperation("find and returns card for given id")
    public Response get(@ApiParam @PathParam("id") String id,@Context HttpHeaders headers){
        try {
            CardView cardView = service.getFor(id);
            URI uri = uriInfo.getAbsolutePathBuilder().path("tasks").build();
            log.info(uri);
            return equalsIgnoreCase(headers.getHeaderString("zip"), "yes")?
                ok(cardView).header("zip","yes").link(uri,"tasks").build():
                ok(cardView).link(uri,"tasks").build();
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
    @Path("/{card}/tasks")
    public TaskApi task(){
        return taskApi;
    }

}
