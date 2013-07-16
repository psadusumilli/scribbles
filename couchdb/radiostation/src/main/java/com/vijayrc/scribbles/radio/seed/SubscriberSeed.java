package com.vijayrc.scribbles.radio.seed;

import com.vijayrc.scribbles.radio.domain.Subscriber;
import com.vijayrc.scribbles.radio.repository.AllSubscribers;
import com.vijayrc.scribbles.radio.seed.base.Seed;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.vijayrc.scribbles.radio.util.Random.between;
import static com.vijayrc.scribbles.radio.util.Random.date;

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
            Subscriber subscriber = new Subscriber("subscriber_" + i, date(2000, 2013), locationSeed.randomLocation());
            allSubscribers.add(subscriber);
            log.info(subscriber);
        }
    }

    public Subscriber randomSubscriber() {
        return allSubscribers.findBySubscriberId("subscriber_" + between(1, 100));
    }
}
