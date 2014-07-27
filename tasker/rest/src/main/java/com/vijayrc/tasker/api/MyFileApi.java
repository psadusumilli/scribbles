package com.vijayrc.tasker.api;

import com.vijayrc.tasker.domain.MyFile;
import com.vijayrc.tasker.error.FileNotFound;
import com.vijayrc.tasker.error.FileNotFoundWebError;
import com.vijayrc.tasker.error.WebError;
import com.vijayrc.tasker.filter.Track;
import com.vijayrc.tasker.service.MyFileService;
import com.vijayrc.tasker.view.CardView;
import com.wordnik.swagger.annotations.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import javax.activation.MimetypesFileTypeMap;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.File;
import java.io.InputStream;

import static org.apache.commons.lang.StringUtils.isBlank;

@Component
@Path("files")
@Track
@Api(value = "files",description = "operations to work on files")
public class MyFileApi {
    @Autowired
    private MyFileService service;
    @Context
    UriInfo uriInfo;

    @GET
    @Path("/{id}")
    @ApiOperation(value = "return file content", notes = "file content is spilled out", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "file bombed"),
            @ApiResponse(code = 404, message = "file not found")
    })
    public Response getFile(@ApiParam(value = "id of file to fetch", required = true) @PathParam("id") String id){
        try {
            MyFile myFile = service.fetch(id);
            return Response.ok(myFile.file(),myFile.mediaType()).build();
        } catch (FileNotFound e) {
            throw new FileNotFoundWebError(id);
        }
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response create(@FormDataParam("file") InputStream inputStream,
                           @FormDataParam("fileName") String fileName,
                           @FormDataParam("file") FormDataContentDisposition fileDetail,
                           @FormDataParam("card") String card){
        try {
            String name = isBlank(fileName)?fileDetail.getFileName():fileName;
            MyFile myFile = MyFile.writeInstance(inputStream,card,name);
            Integer id = service.create(myFile);
            return Response.created(uriInfo.getAbsolutePathBuilder().build(id)).build();
        } catch (Exception e) {
            throw new WebError(e);
        }
    }

}
