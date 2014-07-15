package com.vijayrc.tasker.api;

import com.vijayrc.tasker.error.WebError;
import com.vijayrc.tasker.filter.Track;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.server.ChunkedOutput;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.io.IOException;

import static java.lang.Thread.sleep;

@Component
@Path("logs")
@Track
public class LogApi {
    private static Logger log = LogManager.getLogger(LogApi.class);
    private static int count = 0;
    @GET
    public ChunkedOutput<String> tail(){
        final ChunkedOutput<String> output = new ChunkedOutput<>(String.class);
        new Thread(()->{
            try {
                while(count < 6)
                    output.write(tailFile());
                count = 0;
            } catch (Exception e) {
                throw new WebError(e);
            } finally {
                try {
                    output.close();
                } catch (IOException e) {
                    log.error(e);
                }
            }
        }).start();
        return output;
    }

    //TODO - write a real file tail
    private String tailFile() throws Exception {
        sleep(1000);
        String line = "line-" + count++;
        log.debug(line);
        return line;
    }
}
