package com.vijayrc.scribbles.radio.repository;


import com.vijayrc.scribbles.radio.documents.Location;
import com.vijayrc.scribbles.radio.service.DataSetupService;
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
    @Autowired
    private DataSetupService dataSetupService;

    @Before
    public void dataSetup() throws Exception {
        dataSetupService.run("location");
    }

    @Test
    public void shouldAddALocationAndFindIt(){
        allLocations.add(new Location("atlanta","georgia","usa"));
        Location locationFromDb = allLocations.findByCountryStateAndCity("usa","georgia","atlanta");
        assertNotNull(locationFromDb.getId());
        log.info("Location from db: "+locationFromDb);
        allLocations.remove(locationFromDb);
    }

    @Test
    public void shouldReturnCounts(){
        int count = allLocations.countByCountry();
        log.info("count by countries: "+count);
    }

    @After
    public void dataTearDown(){
        allLocations.removeAll();
    }


}
