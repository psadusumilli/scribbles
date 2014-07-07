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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.File;

@Component
@Path("files")
public class MyFileApi {
    @Autowired
    private MyFileService service;
    @Context
    UriInfo uriInfo;

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

//    @POST
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    public Response create(@FormDataParam("file") File file,
//                           @FormDataParam("card") String card){
//        try {
//            Integer id = service.create(new MyFile(file,card));
//            return Response.created(uriInfo.getAbsolutePathBuilder().build(id)).build();
//        } catch (Exception e) {
//            throw new WebError(e);
//        }
//    }

}
