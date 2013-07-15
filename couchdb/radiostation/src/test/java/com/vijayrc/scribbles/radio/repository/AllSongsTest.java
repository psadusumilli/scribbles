package com.vijayrc.scribbles.radio.repository;

import com.vijayrc.scribbles.radio.domain.Artist;
import com.vijayrc.scribbles.radio.domain.Song;
import com.vijayrc.scribbles.radio.util.Random;
import com.vijayrc.scribbles.radio.domain.Album;
import lombok.extern.log4j.Log4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@Log4j
public class AllSongsTest {
    @Autowired
    private AllSongs allSongs;
    @Autowired
    private AllArtists allArtists;

    public void shouldCheckLinkingOfArtistDocument(){
        Artist artist = new Artist("test_artist",Random.date(1980,1981), location);
        allArtists.add(artist);

        Album album = new Album("test_album",artist, Random.date(2003,2005));
        Song song = new Song("test_song",album,"jazz",1223);
        allSongs.add(song);


    }


}
