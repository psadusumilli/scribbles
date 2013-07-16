package com.vijayrc.scribbles.radio.seed;

import com.vijayrc.scribbles.radio.domain.Album;
import com.vijayrc.scribbles.radio.domain.Artist;
import com.vijayrc.scribbles.radio.repository.AllAlbums;
import com.vijayrc.scribbles.radio.repository.AllArtists;
import com.vijayrc.scribbles.radio.seed.base.Seed;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.vijayrc.scribbles.radio.util.Random.date;

@Component
@Scope("singleton")
@Log4j
public class AlbumSeed {
    @Autowired
    private AllAlbums allAlbums;
    @Autowired
    private ArtistSeed artistSeed;
    @Autowired
    private AllArtists allArtists;

    @Seed(order = 2, description = "albums setup", key = "album")
    public void run() {
        for (int i = 1; i <= 300; i++) {
            Artist artist = artistSeed.random();
            Album album = new Album("album_" + i, artist, date(1930, 2013));
            artist.add(album);
            allArtists.update(artist);
            log.info(album);
        }
    }

}
