package com.vijayrc.scribbles.radio.seed;

import com.vijayrc.scribbles.radio.domain.Artist;
import com.vijayrc.scribbles.radio.repository.AllArtists;
import com.vijayrc.scribbles.radio.seed.base.Seed;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.vijayrc.scribbles.radio.util.Random.between;
import static com.vijayrc.scribbles.radio.util.Random.date;

@Component
@Scope("singleton")
@Log4j
public class ArtistSeed {
    private final int max = 50;

    @Autowired
    private AllArtists allArtists;
    @Autowired
    private LocationSeed locationSeed;

    @Seed(order = 1, description = "artists setup", key = "artist")
    public void run() {
        for (int i = 1; i <= max; i++) {
            Artist artist = new Artist("artist_" + i, date(1930, 2005), locationSeed.random());
            allArtists.add(artist);
            log.info(artist);
        }
    }

    public Artist random() {
        return allArtists.findByName("artist_" + between(1, max));
    }
}

