package com.vijayrc.tasker.repository;

import com.vijayrc.tasker.domain.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;


public class AllTasksTest {
    private static Logger log = LogManager.getLogger(AllTasksTest.class);
    private AllTasks allTasks;

    @Before
    public void setup(){
        allTasks = new AllTasks();
    }
    @Test
    public void shouldPickOneTaskFromAll(){
        String id = allTasks.all().get(0).id();
        Task task = allTasks.getFor(id);
        assertNotNull(task);
    }
}
