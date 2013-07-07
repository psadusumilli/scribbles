package com.vijayrc.scribbles.radio.seed;

import com.vijayrc.scribbles.radio.documents.Location;
import com.vijayrc.scribbles.radio.repository.AllLocations;
import com.vijayrc.scribbles.radio.seed.base.Seed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocationSeed {
    @Autowired
    private AllLocations allLocations;

    @Seed(order = 1, description = "locations setup", key = "Location")
    public void addData() {
        for (int i = 1; i <= 3; i++)
            for (int j = 1; j <= 5; j++)
                for (int k = 1; k <= 7; k++)
                    allLocations.add(new Location("city_" + k, "state_" + j, "country_" + i));
    }


}
