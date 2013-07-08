package com.vijayrc.scribbles.radio.seed;

import com.vijayrc.scribbles.radio.documents.Location;
import com.vijayrc.scribbles.radio.repository.AllLocations;
import com.vijayrc.scribbles.radio.seed.base.Seed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.vijayrc.scribbles.radio.util.Random.*;

@Component
@Scope("singleton")
public class LocationSeed {
    private final int countries = 20;
    private final int states_min = 30;
    private final int states_max = 50;
    private final int cities_min = 100;
    private final int cities_max = 200;
    
    @Autowired
    private AllLocations allLocations;

    @Seed(order = 1, description = "locations setup", key = "location")
    public void run() {
        for (int i = 1; i <= countries; i++)
            for (int j = 1; j <= between(states_min, states_max); j++)
                for (int k = 1; k <= between(cities_min, cities_max); k++)
                    allLocations.add(new Location("city_" + k, "state_" + j, "country_" + i));
    }
}
