package com.vijayrc.scribbles.radio.repository;

import com.vijayrc.scribbles.radio.domain.Album;
import com.vijayrc.scribbles.radio.domain.Artist;
import com.vijayrc.scribbles.radio.seed.LocationSeed;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.vijayrc.scribbles.radio.util.Random.date;
import static org.junit.Assert.assertEquals;

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
    public void shouldAddAlbumsAndFindByName() {
        Artist artist = new Artist("Andrew Bird", date(1930, 2013), locationSeed.random());
        allArtists.add(artist);

        artist.add(new Album("Nervous Tick", artist, date(2012, 2013)));
        artist.add(new Album("Insomnia", artist, date(2012, 2013)));
        allArtists.update(artist);   //saves albums here

        Artist artistFromDb = allArtists.findByName("Andrew Bird");
        assertEquals(2, artistFromDb.getAlbums().size());
        allArtists.remove(artistFromDb);      //does not remove albums
    }
}
