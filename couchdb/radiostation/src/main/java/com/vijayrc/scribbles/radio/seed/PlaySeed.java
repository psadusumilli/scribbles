package com.vijayrc.scribbles.radio.seed;

import com.vijayrc.scribbles.radio.documents.Play;
import com.vijayrc.scribbles.radio.repository.AllPlays;
import com.vijayrc.scribbles.radio.seed.base.Seed;
import com.vijayrc.scribbles.radio.util.Random;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j
public class PlaySeed {
    @Autowired
    private SongSeed songSeed;
    @Autowired
    private SubscriberSeed subscriberSeed;
    @Autowired
    private AllPlays allPlays;

    @Seed(order = 3, description = "play history setup", key = "play")
    public void run() {
        for (int i = 0; i < 200; i++) {
            Play play = new Play(songSeed.randomId(), subscriberSeed.randomId(), Random.date(2005, 2013));
            allPlays.add(play);
            log.info(play);
        }
    }
}
