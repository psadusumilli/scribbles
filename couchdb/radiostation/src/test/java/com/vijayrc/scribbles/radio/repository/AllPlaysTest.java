package com.vijayrc.scribbles.radio.repository;

import com.vijayrc.scribbles.radio.documents.Play;
import com.vijayrc.scribbles.radio.seed.SubscriberSeed;
import com.vijayrc.scribbles.radio.util.Print;
import com.vijayrc.scribbles.radio.vo.Time;
import lombok.extern.log4j.Log4j;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@Log4j
public class AllPlaysTest {
    @Autowired
    private AllPlays allPlays;
    @Autowired
    private SubscriberSeed subscriberSeed;

    @Test
    public void shouldFindPopularSongs(){
        DateTime today = DateTime.now();
        setupSongs(today);
        allPlays.findPopularSongForDay(today);

    }

    @Test
    public void shouldFindSongsPlayedWithinAPeriod(){
        DateTime today = DateTime.now();
        setupSongs(today);
        Time startTime = new Time(today.minusDays(1));
        Time endTime = new Time(today);
        List<String> songsPlayedByTimeRange = allPlays.findSongsPlayedByTimeRange(startTime, endTime);
        Print.the(songsPlayedByTimeRange);
    }

    private void setupSongs(DateTime today) {
        for (int i = 0; i < 20; i++)
            allPlays.add(new Play("song_1", subscriberSeed.randomId(),today ));
        for (int i = 0; i < 10; i++)
            allPlays.add(new Play("song_2", subscriberSeed.randomId(),today ));
        for (int i = 0; i < 5; i++)
            allPlays.add(new Play("song_3", subscriberSeed.randomId(),today ));
    }


}
