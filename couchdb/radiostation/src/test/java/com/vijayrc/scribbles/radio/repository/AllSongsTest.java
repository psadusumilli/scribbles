package com.vijayrc.scribbles.radio.repository;

import com.vijayrc.scribbles.radio.domain.Artist;
import com.vijayrc.scribbles.radio.domain.Song;
import com.vijayrc.scribbles.radio.seed.LocationSeed;
import com.vijayrc.scribbles.radio.domain.Album;
import lombok.extern.log4j.Log4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.vijayrc.scribbles.radio.util.Random.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@Log4j
public class AllSongsTest {
    @Autowired
    private AllSongs allSongs;
    @Autowired
    private AllArtists allArtists;
    @Autowired
    private LocationSeed locationSeed;

    public void shouldCheckLinkingOfArtistDocument(){
        Artist artist = new Artist("test_artist", date(1980, 1981), locationSeed.randomLocation());
        allArtists.add(artist);

        Album album = new Album("test_album",artist, date(2003, 2005));
        Song song = new Song("test_song",album,"jazz",1223);
        allSongs.add(song);


    }


}
