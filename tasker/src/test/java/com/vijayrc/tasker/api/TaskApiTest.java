package com.vijayrc.tasker.api;

import com.vijayrc.tasker.view.TaskView;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@Log4j2
@Ignore
public class TaskApiTest {
    private Client client;
    private WebTarget target;

    @Before
    public void setup(){
        client = ClientBuilder.newClient();
        target = client.target("http://localhost:9090/tasker/rest/").path("tasks");
    }
    @Test
    public void shouldReturnAllTasksAsTypeFromXml(){
        List<TaskView> taskViews = target.request().get(new GenericType<List<TaskView>>(){});
        assertNotNull(taskViews);
    }
    @Test
    public void shouldReturnAllTasksAsXMLAndJson(){
        Response response = target.request().get();
        log.info(response.readEntity(String.class));

        Response jsonResponse = target.request().accept("application/json").get();
        log.info(jsonResponse.readEntity(String.class));
    }

    @Test
    public void shouldReturn500ForExceptionInAllTasks(){

    }
    @Test
    public void shouldReturn404ForTaskNotFound(){

    }
    @Test
    public void shouldReturnATaskInXMLAndJson(){

    }
    @Test
    public void shouldUpdateTask(){

    }
    @Test
    public void shouldDeleteTask(){

    }
}
