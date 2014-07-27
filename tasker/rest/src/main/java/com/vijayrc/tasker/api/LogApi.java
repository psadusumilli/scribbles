package com.vijayrc.tasker.api;

import com.vijayrc.tasker.error.WebError;
import com.vijayrc.tasker.filter.Track;
import com.vijayrc.tasker.service.MyFileService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseBroadcaster;
import org.glassfish.jersey.media.sse.SseFeature;
import org.glassfish.jersey.server.ChunkedOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

import static java.lang.Thread.sleep;

@Component
@Scope("singleton")
@Singleton //required for broadcast
@Path("logs")
@Track
@Api(value = "logs", description = "Operations about tailing log files using asynch mechanism")
public class LogApi {
    private static Logger log = LogManager.getLogger(LogApi.class);
    private final SseBroadcaster broadcaster = new SseBroadcaster();

    @Autowired
    private MyFileService service;

    @GET
    @Path("/{id}")
    @ApiOperation(value = "tail",notes = "tail logs using chunked output", response = ChunkedOutput.class,consumes = "string id of the file")
    public ChunkedOutput<String> tail(@ApiParam(required = true, value = "unique id of the file") @PathParam("id") String id){
        final ChunkedOutput<String> output = new ChunkedOutput<>(String.class);
        new Thread(()->{
            try {
                for (String line : service.read(id)) {
                    output.write(line+"\n");
                    log.debug(line);
                    sleep(100);
                }
            } catch(Exception e){
                throw new WebError(e);
            }
            finally {
                try {
                    output.close();
                } catch (IOException e) {
                    log.error(e);
                }
            }
        }).start();
        log.info("output returned");
        return output;
    }

    @GET
    @Path("/sse/{id}")
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    @ApiOperation(value = "tailEvents",notes = "tail logs using sse ", response = ChunkedOutput.class,consumes = "string id of the file")
    public EventOutput tailEvents(@ApiParam(required = true, value = "unique id of the file") @PathParam("id") String id){
        final EventOutput output = new EventOutput();
        new Thread(()->{
            try {
                for (String line : service.read(id)) {
                    final OutboundEvent.Builder builder = new OutboundEvent.Builder();
                    builder.name("tail-event");
                    builder.data(String.class,line);
                    output.write(builder.build());
                    log.debug(line);
                    sleep(100);
                }
            } catch(Exception e){
                throw new WebError(e);
            }
            finally {
                try {
                    output.close();
                } catch (IOException e) {
                    log.error(e);
                }
            }

        }).start();
        log.info("output returned");
        return output;
    }

    @POST
    @Path("/broadcast/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String startBroadcast(@PathParam("id") String id, @DefaultValue("400") @MatrixParam("delay") Integer delay){
        log.info("id="+id+"|delay="+delay);
        new Thread(() -> {
            try {
                for (String line : service.read(id)) {
                    final OutboundEvent.Builder builder = new OutboundEvent.Builder();
                    builder.name("radio-event");
                    builder.data(String.class,line);
                    broadcaster.broadcast(builder.build());
                    log.debug(line);
                    sleep(delay);
                }
            } catch(Exception e){
                throw new WebError(e);
            }finally {
                broadcaster.closeAll();
                log.info("closed all listeners");
            }
        }).start();
        System.out.println(this);
        return "broadcast started|"+id;
    }

    @GET
    @Path("/broadcast/")
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    public EventOutput registerForBroadcast(){
        final EventOutput eventOutput = new EventOutput();
        this.broadcaster.add(eventOutput);
        log.info("registered to read");
        return eventOutput;
    }


}
