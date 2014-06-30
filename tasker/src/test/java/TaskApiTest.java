import com.vijayrc.tasker.view.TaskView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class TaskApiTest {
    private static Logger log = LogManager.getLogger(TaskApiTest.class);
    private Client client;

    @Before
    public void setup(){
        client = ClientBuilder.newClient();
    }

    @Test
    public void shouldReturnAllTasks(){
        WebTarget target = client.target(TestConfig.baseUrl).path("tasks");
        List<TaskView> taskViews = target.request().get(new GenericType<List<TaskView>>(){});
        assertNotNull(taskViews);
        taskViews.forEach(log::info);
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
