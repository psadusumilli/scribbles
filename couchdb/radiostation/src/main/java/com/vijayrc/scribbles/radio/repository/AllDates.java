package com.vijayrc.scribbles.radio.repository;

import org.joda.time.DateTime;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.GregorianCalendar;

import static java.util.GregorianCalendar.*;

@Repository
@Scope("singleton")
public class AllDates {

    private GregorianCalendar calendar = new GregorianCalendar();

    public DateTime random() {
        calendar.set(YEAR, randomBetween(1930, 2013));
        calendar.set(DAY_OF_YEAR, randomBetween(1, calendar.getActualMaximum(DAY_OF_YEAR)));
        return new DateTime(calendar.getTime());
    }

    private int randomBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }
}
