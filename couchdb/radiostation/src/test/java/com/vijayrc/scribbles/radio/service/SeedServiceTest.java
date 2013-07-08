package com.vijayrc.scribbles.radio.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SeedServiceTest {

    @Autowired
    private SeedService seedService;

    @Test
    public void shouldLoadData() throws Exception {
        seedService.run("artist","song");
    }


}
