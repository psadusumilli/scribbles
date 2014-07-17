package com.vijayrc.tasker.api;

import com.vijayrc.tasker.error.WebError;
import com.vijayrc.tasker.filter.Track;
import com.vijayrc.tasker.service.MyFileService;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;
import org.glassfish.jersey.server.ChunkedOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.io.IOException;
import java.util.List;

import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;

@Component
@Path("logs")
@Track
public class LogApi {
    private static Logger log = LogManager.getLogger(LogApi.class);

    @Autowired
    private MyFileService service;

    @GET
    @Path("{id}")
    public ChunkedOutput<String> tail(@PathParam("id") String id){
        final ChunkedOutput<String> output = new ChunkedOutput<>(String.class);
        new Thread(()->{
            try {
                List<String> lines = service.read(id);
                for (String line : lines) {
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
    @Path("sse/{id}")
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    public EventOutput tailEvents(@PathParam("id") String id){
        final EventOutput output = new EventOutput();
        new Thread(()->{
            try {
                List<String> lines = service.read(id);
                for (String line : lines) {
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

}
