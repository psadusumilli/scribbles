package com.vijayrc.scribbles.radio.repository;

import com.vijayrc.scribbles.radio.documents.Artist;
import com.vijayrc.scribbles.radio.util.Random;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@Log4j
public class AllArtistsTest {
    @Autowired
    private AllArtists allArtists;

    @Test
    public void shouldAddAndFindByName() {
        allArtists.add(new Artist("Andrew Bird", Random.date(1930, 2013)));
        Artist artist = allArtists.findByName("Andrew Bird");
        assertNotNull(artist.getId());
        allArtists.remove(artist);
    }
}
