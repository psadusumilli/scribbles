package com.vijayrc.scribbles.radio.repository;


import com.vijayrc.scribbles.radio.documents.Location;
import com.vijayrc.scribbles.radio.service.SeedService;
import com.vijayrc.scribbles.radio.util.Print;
import lombok.extern.log4j.Log4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@Log4j
public class AllLocationsTest {
    @Autowired
    private AllLocations allLocations;
    @Autowired
    private SeedService seedService;

    @Before
    public void dataSetup() throws Exception {
        //seedService.run("location");
    }

    @Test
    public void shouldAddALocationAndFindIt() {
        allLocations.add(new Location("atlanta", "georgia", "usa"));
        Location locationFromDb = allLocations.findByCountryStateAndCity("usa", "georgia", "atlanta");
        assertNotNull(locationFromDb.getId());
        log.info("Location from db: " + locationFromDb);
        allLocations.remove(locationFromDb);
    }

    @Test
    public void shouldFindLocationsWithinGivenARange() {
        Object[] startKey = new Object[]{"country_1", "state_1"};
        Object[] endKey = new Object[]{"country_2", "state_2", "city_3"};
        List<Location> locations = allLocations.findByRange(startKey, endKey);
        assertNotNull(locations);
        Print.the(locations);

        log.info("using find view: " + locations.size());
        log.info("using count view: " + allLocations.countByRange(startKey, endKey));
    }

    @Test
    public void shouldReturnCounts() {
        log.info("states count by country:");
        Print.the(allLocations.statesCountByCountry());
        log.info("cities count by state:");
        Print.the(allLocations.citiesCountByStateAndCountry());
    }

    @After
    public void dataTearDown() {
        //allLocations.removeAll();
    }


}
