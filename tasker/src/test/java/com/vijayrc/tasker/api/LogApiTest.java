package com.vijayrc.tasker.api;

import com.vijayrc.tasker.config.TestMaker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.client.ChunkedInput;
import org.junit.Test;

import javax.ws.rs.client.AsyncInvoker;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.concurrent.Future;

public class LogApiTest {
    private static Logger log = LogManager.getLogger(LogApiTest.class);

    @Test
    public void shouldReadFromChunkedResponseButWaitUntilEnd() throws Exception {
        AsyncInvoker invoker = TestMaker.target().path("/logs").request().async();
        Future<Response> future = invoker.get();
        while(!future.isDone()){
            String line= future.get().readEntity(String.class);
            log.info(line);
        }
    }
    @Test
    public void shouldReadFromChunkedResponsePieceMeal() throws Exception {
        Response response = TestMaker.target().path("/logs").request().get();
        ChunkedInput<String> chunkedInput = response.readEntity(new GenericType<ChunkedInput<String>>(){});
        String chunk;
        while((chunk = chunkedInput.read()) != null)
            log.info("client|" + chunk);
    }
}
