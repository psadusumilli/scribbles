package com.vijayrc.scribbles.radio.seed;

import com.vijayrc.scribbles.radio.dimension.Location;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.vijayrc.scribbles.radio.util.Random.between;

@Component
@Scope("singleton")
@Log4j
public class LocationSeed {
    public Location random() {
        return new Location("city_" + between(10, 20), "state_" + between(3, 5), "country_" + between(1, 2));
    }
}
