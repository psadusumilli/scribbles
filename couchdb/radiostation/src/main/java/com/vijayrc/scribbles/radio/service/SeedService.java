package com.vijayrc.scribbles.radio.service;

import com.vijayrc.scribbles.radio.seed.base.AllSeeds;
import lombok.extern.log4j.Log4j;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import static org.joda.time.DateTime.now;


@Service
@Scope("singleton")
@Log4j
public class SeedService {
    @Autowired
    private AllSeeds allSeeds;

    public void run(String... keys) throws Exception {
        DateTime start = now();
        SeedService.log.info("setup start: " + start);

        allSeeds.run(keys);

        DateTime end = now();
        Period p = new Period(start, end);
        SeedService.log.info("setup end: " + end+"|time taken: " + p);
    }
}
