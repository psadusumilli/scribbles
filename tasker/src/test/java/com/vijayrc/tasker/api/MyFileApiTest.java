package com.vijayrc.tasker.api;

import com.vijayrc.tasker.config.TestMaker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.junit.Before;
import org.junit.Test;

import javax.activation.MimetypesFileTypeMap;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;

import static com.vijayrc.tasker.config.TestMaker.baseUrl;
import static java.lang.System.getProperty;

public class MyFileApiTest {
    private Client client;
    private WebTarget target;

    @Before
    public void setup(){
        client = TestMaker.client();
        target = client.target(baseUrl()).path("files/upload");
    }

    @Test
    public void shouldCreateANewFile(){
        File file = new File(getProperty("user.dir") + "/files/captmidn.txt");
        String contentType = new MimetypesFileTypeMap().getContentType(file);
        FormDataMultiPart multiPart = new FormDataMultiPart()
                .field("card","1")
                .field("fileName","captmidn.txt")
                .field("file", file, MediaType.valueOf(contentType));
        Response response = target.request().post(Entity.entity(multiPart, multiPart.getMediaType()));
        String s = response.readEntity(String.class);
        System.out.println(s);
    }

}
