package com.vijayrc.scribbles.radio.repository;

import com.vijayrc.scribbles.radio.documents.Subscriber;
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
public class AllSubscribersTest {
    @Autowired
    private AllSubscribers allSubscribers;

    @Test
    public void shouldPickSubscriberWithInADateRange(){
        DateTime end = DateTime.now();
        DateTime start = end.minusYears(1);
        Object[] startKey = new Time(start).asArrayKey();
        Object[] endKey = new Time(end).asArrayKey();

        List<Subscriber> subscribers = allSubscribers.findByRange(startKey, endKey);
        Print.the(subscribers);
    }

}
