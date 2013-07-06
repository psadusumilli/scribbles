package com.vijayrc.scribbles.radio.repository;


import com.vijayrc.scribbles.radio.documents.Location;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class AllLocationsTest {

    @Autowired
    private AllLocations allLocations;

    @Test
    public void shouldAddALocation(){
        Location location = new Location("atlanta","georgia","usa");
        allLocations.add(location);
    }


}
