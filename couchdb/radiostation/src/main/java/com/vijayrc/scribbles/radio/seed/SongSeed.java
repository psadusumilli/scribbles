package com.vijayrc.scribbles.radio.seed;

import com.vijayrc.scribbles.radio.documents.Song;
import com.vijayrc.scribbles.radio.repository.AllSongs;
import com.vijayrc.scribbles.radio.seed.base.Seed;
import com.vijayrc.scribbles.radio.util.Random;
import com.vijayrc.scribbles.radio.vo.Album;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
@Log4j
public class SongSeed {
    private final int songs = 100;

    @Autowired
    private AllSongs allSongs;
    @Autowired
    private ArtistSeed artistSeed;

    @Seed(order = 2, description = "songs setup", key = "song")
    public void run() {
        Album album = album(1);
        for (int i = 1, k = 1; i <= songs; i++) {
            if (i % Random.between(5, 9) == 0) {
                ++k;
                album = album(k);
            }
            Song song = new Song("song_" + i, album, genre(), duration());
            allSongs.add(song);
            log.info(song);
        }
    }

    private Integer duration() {
        return Random.between(300, 700);
    }

    private String genre() {
        return "genre_" + Random.between(1, 9);
    }

    private Album album(int k) {
        return new Album("album_" + k, artistSeed.random(), Random.date());
    }

}
