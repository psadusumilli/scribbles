package com.vijayrc.scribbles.radio.seed;

import com.vijayrc.scribbles.radio.documents.Location;
import com.vijayrc.scribbles.radio.repository.AllLocations;
import com.vijayrc.scribbles.radio.seed.base.Seed;
import com.vijayrc.scribbles.radio.util.Random;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.vijayrc.scribbles.radio.util.Random.*;

@Component
@Scope("singleton")
@Log4j
public class LocationSeed {
    @Autowired
    private AllLocations allLocations;

    @Seed(order = 1, description = "locations setup", key = "location")
    public void run() {
        for (int i = 1; i <= 2; i++)
            for (int j = 1; j <= between(3, 5); j++)
                for (int k = 1; k <= between(10, 20); k++) {
                    Location location = new Location("city_" + k, "state_" + j, "country_" + i);
                    allLocations.add(location);
                    log.info(location);
                }
    }

    public Location random() {
        return allLocations.findByCountryStateAndCity(
                "country_" + between(1, 2),
                "state_" + between(3, 5),
                "city_" + between(10, 20));
    }
}
