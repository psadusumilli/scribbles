package com.vijayrc.tasker.api;

import com.vijayrc.tasker.view.TaskView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.List;

import static com.vijayrc.tasker.config.TestConfig.baseUrl;
import static org.junit.Assert.assertNotNull;

public class TaskApiTest {
    private static Logger log = LogManager.getLogger(TaskApiTest.class);
    private Client client;
    private WebTarget target;

    @Before
    public void setup(){
        client = ClientBuilder.newClient();
        target = client.target(baseUrl).path("tasks");
    }
    @Test
    public void shouldReturnAllTasksAsTypeFromXml(){
        List<TaskView> taskViews = target.request().get(new GenericType<List<TaskView>>(){});
        assertNotNull(taskViews);
        taskViews.forEach(log::info);
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
