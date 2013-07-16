package com.vijayrc.scribbles.radio.seed;

import com.vijayrc.scribbles.radio.domain.Album;
import com.vijayrc.scribbles.radio.domain.Song;
import com.vijayrc.scribbles.radio.repository.AllAlbums;
import com.vijayrc.scribbles.radio.repository.AllSongs;
import com.vijayrc.scribbles.radio.seed.base.Seed;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.vijayrc.scribbles.radio.util.Random.between;

@Component
@Scope("singleton")
@Log4j
public class SongSeed {
    private final int totalNumberOfSongs = 500;

    @Autowired
    private AllSongs allSongs;
    @Autowired
    private AllAlbums allAlbums;

    @Seed(order = 3, description = "songs setup", key = "song")
    public void run() {
        Album album = album(1);
        for (int i = 1, k = 1; i <= totalNumberOfSongs; i++) {
            if (i % between(5, 9) == 0) {
                ++k;
                album = album(k);
            }
            Song song = new Song("song_" + i, album, "genre_" + between(1, 9), between(300, 700));
            allSongs.add(song);
            log.info(song);
        }
    }

    private Album album(int k) {
        return allAlbums.findByName("album_" + k);
    }

    public Song randomSong() {
        return allSongs.findByName("song_" + between(1, totalNumberOfSongs));
    }
}
