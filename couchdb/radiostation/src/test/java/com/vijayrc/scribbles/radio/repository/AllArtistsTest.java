package com.vijayrc.scribbles.radio.repository;

import com.vijayrc.scribbles.radio.domain.Album;
import com.vijayrc.scribbles.radio.domain.Artist;
import com.vijayrc.scribbles.radio.seed.LocationSeed;
import com.vijayrc.scribbles.radio.util.Random;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.vijayrc.scribbles.radio.util.Random.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@Log4j
public class AllArtistsTest {
    @Autowired
    private AllArtists allArtists;
    @Autowired
    private AllAlbums allAlbums;
    @Autowired
    private LocationSeed locationSeed;

    @Test
    public void shouldAddAndFindByName() {
        Artist artist = new Artist("Andrew Bird", date(1930, 2013), locationSeed.random());
        allArtists.add(artist);

        artist.add(new Album("Nervous Tick", artist, date(2012, 2013)));
        artist.add(new Album("Insomnia", artist, date(2012, 2013)));
        allArtists.update(artist);

        Artist artistFromDb = allArtists.findByName("Andrew Bird");
        assertEquals(2, artistFromDb.getAlbums().size());
//        allArtists.remove(artistFromDb);
    }
}
