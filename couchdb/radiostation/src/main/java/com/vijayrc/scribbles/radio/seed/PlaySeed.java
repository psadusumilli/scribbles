package com.vijayrc.scribbles.radio.seed;

import com.vijayrc.scribbles.radio.domain.Play;
import com.vijayrc.scribbles.radio.repository.AllPlays;
import com.vijayrc.scribbles.radio.repository.AllSubscribers;
import com.vijayrc.scribbles.radio.seed.base.Seed;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.vijayrc.scribbles.radio.util.Random.date;

@Component
@Log4j
public class PlaySeed {
    @Autowired
    private SongSeed songSeed;
    @Autowired
    private SubscriberSeed subscriberSeed;
    @Autowired
    private AllPlays allPlays;
    @Autowired
    private AllSubscribers allSubscribers;

    @Seed(order = 4, description = "play history setup", key = "play")
    public void run() {
        for (int i = 0; i < 1000; i++) {
            Play play = new Play(songSeed.randomSong(), subscriberSeed.randomSubscriber(), date(2005, 2013));
            allPlays.add(play);
            log.info(play);
        }
    }
}
