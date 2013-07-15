package com.vijayrc.scribbles.radio.seed;

import com.vijayrc.scribbles.radio.domain.Subscriber;
import com.vijayrc.scribbles.radio.repository.AllSubscribers;
import com.vijayrc.scribbles.radio.seed.base.Seed;
import com.vijayrc.scribbles.radio.util.Random;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
@Log4j
public class SubscriberSeed {
    @Autowired
    private AllSubscribers allSubscribers;
    @Autowired
    private LocationSeed locationSeed;
    @Autowired
    private SongSeed songSeed;

    @Seed(order = 2, description = "subscribers setup", key = "subscriber")
    public void run() {
        for (int i = 1; i <= 100; i++) {
            Subscriber subscriber = new Subscriber("subscriber_" + i, Random.date(2000, 2013), locationSeed.random());
            allSubscribers.add(subscriber);
            log.info(subscriber);
        }
    }

    public String randomSubscriberId() {
        return "subscriber_" + Random.between(1, 100);
    }
}
