package com.vijayrc.scribbles.radio.repository;


import com.vijayrc.scribbles.radio.documents.Location;
import com.vijayrc.scribbles.radio.service.DataSetupService;
import lombok.extern.log4j.Log4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

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
        //dataSetupService.run("location");
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
        for (Location location : locations)
            log.info(location);
    }

    @Test
    public void shouldReturnCounts() {
        log.info("states count by country:\n" + print(allLocations.statesCountByCountry()));
        log.info("cities count by state: \n" + print(allLocations.citiesCountByStateAndCountry()));
    }

    private String print(Map map) {
        String str = "";
        for (Object key : map.keySet())
            str = str + key + "=>" + map.get(key) + "\n";
        return str;
    }

    @After
    public void dataTearDown() {
        //allLocations.removeAll();
    }


}
