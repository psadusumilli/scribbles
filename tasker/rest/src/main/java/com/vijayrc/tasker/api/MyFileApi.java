package com.vijayrc.tasker.api;

import com.vijayrc.tasker.domain.MyFile;
import com.vijayrc.tasker.error.FileNotFound;
import com.vijayrc.tasker.error.FileNotFoundWebError;
import com.vijayrc.tasker.error.WebError;
import com.vijayrc.tasker.service.MyFileService;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.activation.MimetypesFileTypeMap;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.File;
import java.io.InputStream;

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

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response create(@FormDataParam("file") InputStream file,
                           @FormDataParam("file") FormDataContentDisposition fileDetail,
                           @FormDataParam("card") String card){
        try {
            System.out.println(file.available()+"|"+card+"|"+fileDetail.getName());
//            Integer id = service.create(new MyFile(file,card));
//            return Response.created(uriInfo.getAbsolutePathBuilder().build(id)).build();
            return Response.ok().build();
        } catch (Exception e) {
            throw new WebError(e);
        }
    }

}
