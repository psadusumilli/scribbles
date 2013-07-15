package com.vijayrc.scribbles.radio.seed;

import com.vijayrc.scribbles.radio.domain.Album;
import com.vijayrc.scribbles.radio.repository.AllAlbums;
import com.vijayrc.scribbles.radio.seed.base.Seed;
import com.vijayrc.scribbles.radio.util.Random;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
@Log4j
public class AlbumSeed {
    private final int max = 300;

    @Autowired
    private AllAlbums allAlbums;
    @Autowired
    private ArtistSeed artistSeed;

    @Seed(order = 2, description = "albums setup", key = "album")
    public void run() {
        for (int i = 1; i <= max; i++)
            allAlbums.add(new Album("album_" + i, artistSeed.random(), Random.date(1930, 2013)));
    }

}
