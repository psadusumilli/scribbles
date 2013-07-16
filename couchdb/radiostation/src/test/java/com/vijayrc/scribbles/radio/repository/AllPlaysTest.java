package com.vijayrc.scribbles.radio.repository;

import com.vijayrc.scribbles.radio.dimension.Time;
import com.vijayrc.scribbles.radio.domain.Play;
import com.vijayrc.scribbles.radio.domain.Song;
import com.vijayrc.scribbles.radio.seed.SongSeed;
import com.vijayrc.scribbles.radio.seed.SubscriberSeed;
import com.vijayrc.scribbles.radio.util.Print;
import lombok.extern.log4j.Log4j;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.sort;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@Log4j
public class AllPlaysTest {
    @Autowired
    private AllPlays allPlays;
    @Autowired
    private SubscriberSeed subscriberSeed;
    @Autowired
    private SongSeed songSeed;

    @Test
    public void shouldFindPopularSongWithinATimeRangeFromASpecificState() {
        DateTime today = DateTime.now();
        setupSongs(today);
        Time startTime = new Time(today.minusMinutes(2));
        Time endTime = new Time(today);

        List<Song> songsForTimeRange = allPlays.findSongsPlayedByTimeRange(startTime, endTime);
        Print.the(sort(songsForTimeRange,on(Song.class).getUniqueId()));
    }


    private void setupSongs(DateTime today) {
        Song song1 = songSeed.randomSong();
        Song song2 = songSeed.randomSong();
        Song song3 = songSeed.randomSong();
        for (int i = 0; i < 20; i++)
            allPlays.add(new Play(song1, subscriberSeed.randomSubscriber(), today));
        for (int i = 0; i < 10; i++)
            allPlays.add(new Play(song2, subscriberSeed.randomSubscriber(), today));
        for (int i = 0; i < 5; i++)
            allPlays.add(new Play(song3, subscriberSeed.randomSubscriber(), today));
    }
}
