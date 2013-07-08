package com.vijayrc.scribbles.radio.seed;

import com.vijayrc.scribbles.radio.documents.Artist;
import com.vijayrc.scribbles.radio.repository.AllArtists;
import com.vijayrc.scribbles.radio.seed.base.Seed;
import com.vijayrc.scribbles.radio.util.Random;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
@Log4j
public class ArtistSeed {
    @Autowired
    private AllArtists allArtists;

    @Seed(order = 1, description = "artists setup", key = "artist")
    public void run() {
        for (int i = 1; i <= 50; i++) {
            Artist artist = new Artist("artist_" + i, Random.date(1930, 2005));
            allArtists.add(artist);
            log.info(artist);
        }
    }

    public Artist random() {
        return allArtists.findByName("artist_" + Random.between(1, 50));
    }
}

