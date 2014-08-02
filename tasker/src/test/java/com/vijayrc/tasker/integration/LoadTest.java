package com.vijayrc.tasker.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class LoadTest {

    @Test
    public void shouldLoadAllBeans() {
        // Given


        // When


        // Then
        assertTrue(true);
    }
}
