package com.vijayrc.tasker.api;

import com.vijayrc.tasker.view.CardView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import static com.vijayrc.tasker.config.TestConfig.baseUrl;
import static org.junit.Assert.assertNotNull;

public class CardApiTest {
    private static Logger log = LogManager.getLogger(CardApiTest.class);
    private Client client;
    private WebTarget target;

    @Before
    public void setup(){
        client = ClientBuilder.newClient();
        target = client.target(baseUrl).path("cards");
    }
    @Test
    public void shouldReturnAllTasksAsTypeFromXml(){
        List<CardView> cardViews = target.request().get(new GenericType<List<CardView>>(){});
        assertNotNull(cardViews);
    }
    @Test
    public void shouldReturnAllTasksAsXMLAndJson(){
        Response xmlResponse = target.request().get();
        log.info(xmlResponse.readEntity(String.class));

        Response jsonResponse = target.request().accept("application/json").get();
        log.info(jsonResponse.readEntity(String.class));
    }
    @Test
    public void shouldUseABeanParam(){
        target = client.target(baseUrl).path("cards/filter/title;key=tech").queryParam("format","json");
        Response response = target.request().get();
        log.info(response.readEntity(String.class));
    }

    @Test
    public void shouldReturn404ForCardNotFound(){

    }
    @Test
    public void shouldCreateACard(){
        CardView cardView = new CardView().title("card-x").summary("summary-x").startBy(new Date()).endBy(new Date());
        CardView cardViewSaved = target.request().post(Entity.entity(cardView, "application/json"), CardView.class);
        log.info(cardViewSaved);
    }
    @Test
    public void shouldDeleteACard(){
        Response response = target.path("/5").request().delete();
        log.info(response);
    }
}
