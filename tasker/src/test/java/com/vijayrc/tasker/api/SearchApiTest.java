package com.vijayrc.tasker.api;

import com.vijayrc.tasker.config.TestMaker;
import com.vijayrc.tasker.view.SearchView;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import java.util.List;

import static com.vijayrc.tasker.config.TestMaker.baseUrl;
import static org.junit.Assert.assertFalse;

public class SearchApiTest {
    private Client client;
    private WebTarget target;

    @Before
    public void setup(){
        client = TestMaker.client();
        target = client.target(baseUrl()).path("/search;key=123");
    }

    @Test
    public void shouldMakeAsynchCall(){
        List<SearchView> searchViews = target.request().get(new GenericType<List<SearchView>>(){});
        assertFalse(searchViews.isEmpty());
    }

}
