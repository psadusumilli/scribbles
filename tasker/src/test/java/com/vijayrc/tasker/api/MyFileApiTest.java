package com.vijayrc.tasker.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.junit.Before;
import org.junit.Test;

import javax.activation.MimetypesFileTypeMap;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;

import static com.vijayrc.tasker.config.TestConfig.baseUrl;
import static java.lang.System.getProperty;

public class MyFileApiTest {
    private static Logger log = LogManager.getLogger(MyFileApiTest.class);
    private Client client;
    private WebTarget target;

    @Before
    public void setup(){
        client = ClientBuilder.newClient().register(MultiPartFeature.class);
        target = client.target(baseUrl).path("files/upload");
    }

    @Test
    public void shouldCreateANewFile(){
        File file = new File(getProperty("user.dir") + "/files/end.png");
        String contentType = new MimetypesFileTypeMap().getContentType(file);
        FormDataMultiPart multiPart = new FormDataMultiPart()
                .field("card","1")
                .field("file", file, MediaType.valueOf(contentType));
        Response response = target.request().post(Entity.entity(multiPart, multiPart.getMediaType()));
        String s = response.readEntity(String.class);
        System.out.println(s);
    }

}
