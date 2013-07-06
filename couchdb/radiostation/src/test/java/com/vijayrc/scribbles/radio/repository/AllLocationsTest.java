package com.vijayrc.scribbles.radio.repository;


import com.vijayrc.scribbles.radio.documents.Location;
import lombok.extern.log4j.Log4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@Log4j
public class AllLocationsTest {

    @Autowired
    private AllLocations allLocations;

    @Test
    public void shouldAddALocationAndFindIt(){
        Location location = new Location("atlanta","georgia","usa");
        allLocations.add(location);
        Location locationFromDb = allLocations.findByCountryStateAndCity("usa","georgia","atlanta");
        assertNotNull(locationFromDb.getId());
        log.info(location);
    }


}
