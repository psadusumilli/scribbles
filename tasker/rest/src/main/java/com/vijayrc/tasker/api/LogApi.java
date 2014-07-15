package com.vijayrc.tasker.api;

import com.vijayrc.tasker.error.WebError;
import com.vijayrc.tasker.filter.Track;
import com.vijayrc.tasker.service.MyFileService;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.server.ChunkedOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.io.IOException;
import java.util.List;

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
                    Thread.sleep(200);
                }
            } catch(Exception e){
                throw new WebError(e);
            }
            finally {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        log.info("request returned");
        return output;
    }

}
