package com.vijayrc.tasker.api;

import com.vijayrc.tasker.domain.MyFile;
import com.vijayrc.tasker.error.FileNotFound;
import com.vijayrc.tasker.error.FileNotFoundWebError;
import com.vijayrc.tasker.service.MyFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.activation.MimetypesFileTypeMap;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;

@Component
@Path("files")
public class MyFileApi {
    @Autowired
    private MyFileService service;

    @GET
    @Path("{id}")
    public Response getFile(@PathParam("id") String id){
        try {
            MyFile myFile = service.fetch(id);
            File file = myFile.file();
            String mediaType = new MimetypesFileTypeMap().getContentType(file);
            return Response.ok(file,mediaType).build();
        } catch (FileNotFound e) {
            throw new FileNotFoundWebError(id);
        }
    }


}
