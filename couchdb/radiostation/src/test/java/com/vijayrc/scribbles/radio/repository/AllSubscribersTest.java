package com.vijayrc.scribbles.radio.repository;

import com.vijayrc.scribbles.radio.aggregate.PlayHistory;
import com.vijayrc.scribbles.radio.dimension.Location;
import com.vijayrc.scribbles.radio.domain.Subscriber;
import com.vijayrc.scribbles.radio.util.Print;
import lombok.extern.log4j.Log4j;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@Log4j
public class AllSubscribersTest {
    @Autowired
    private AllSubscribers allSubscribers;

    @Test
    public void shouldPickWithInADateRange() {
        DateTime end = DateTime.now();
        DateTime start = end.minusYears(1);
        List<Subscriber> subscribers = allSubscribers.findByDateRange(start, end);

        Print.the(subscribers);
        log.info("count by doc view: " + subscribers.size());
        log.info("count by reduce view: " +allSubscribers.countByDateRange(start,end));
    }

    @Test
    public void shouldPickWithInALocationRange() {
        Location location1 = new Location("country_2", "state_5", "city_2");
        Location location2 = new Location("country_2", "state_5", "city_20");
        List<Subscriber> subscribers = allSubscribers.findByLocationRange(location1, location2);

        Print.the(subscribers);
        log.info("count by doc view: " + subscribers.size());
        log.info("count by reduce view: " + allSubscribers.countByLocationRange(location1, location2));
        log.info("count by country: ");
        Print.the(allSubscribers.countByCountry());
        log.info("count by state: ");
        Print.the(allSubscribers.countByState());
    }

    @Test
    public void shouldShowPlayListUsingViewCollation() {
        PlayHistory playHistory = allSubscribers.findPlayHistory("subscriber_1");
        assertNotNull(playHistory);
        log.info("play history: " + playHistory);
    }

}
