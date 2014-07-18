package com.vijayrc.tasker.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.client.ChunkedInput;
import org.glassfish.jersey.media.sse.EventInput;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.junit.Test;

import javax.ws.rs.client.AsyncInvoker;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static com.vijayrc.tasker.config.TestMaker.target;
import static junit.framework.Assert.assertEquals;

public class LogApiTest {
    private static Logger log = LogManager.getLogger(LogApiTest.class);

    @Test
    public void shouldReadFromChunkedResponseButWaitUntilEnd() throws Exception {
        AsyncInvoker invoker = target().path("/logs/2").request().async();
        Future<Response> future = invoker.get();
        while(!future.isDone()){
            String line= future.get().readEntity(String.class);
            log.info(line);
        }
    }
    @Test
    public void shouldReadFromChunkedResponsePieceMeal() throws Exception {
        Response response = target().path("/logs/2").request().get();
        ChunkedInput<String> chunkedInput = response.readEntity(new GenericType<ChunkedInput<String>>(){});
        chunkedInput.setParser(ChunkedInput.createParser("\n"));

        String chunk;
        while((chunk = chunkedInput.read()) != null)
            log.info(chunk+"|"+chunk.length());
    }

    @Test
    public void shouldReadFromServerEvents() throws Exception {
        EventInput eventInput = target().path("/logs/sse/2").request().get(EventInput.class);
        while(!eventInput.isClosed()){
            InboundEvent event = eventInput.read();
            if(event == null) break;
            log.info(event.getName()+"|"+event.readData(String.class));
        }
    }

    @Test
    public void shouldReadFromBroadcastEvents() throws Exception {
        String message = target().path("/logs/broadcast/2;delay=600").request().post(Entity.entity("", MediaType.TEXT_PLAIN),String.class);
        assertEquals("broadcast started|2", message);

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for(int i=1;i<6;i++){
            executorService.submit(()->{
                EventInput eventInput = target().path("/logs/broadcast").request().get(EventInput.class);
                while(!eventInput.isClosed()){
                    InboundEvent event = eventInput.read();
                    System.out.println(event);
                    if(event == null) break;
                    log.info("read|"+event.getName()+"|"+event.readData(String.class));
                }
            });
        }
        log.info("waiting...");
        executorService.awaitTermination(3000, TimeUnit.SECONDS);
        log.info("all done");
    }
}
