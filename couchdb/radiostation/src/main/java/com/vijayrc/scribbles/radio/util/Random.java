package com.vijayrc.scribbles.radio.util;

import org.joda.time.DateTime;

import java.util.GregorianCalendar;

import static java.util.Calendar.DAY_OF_YEAR;
import static java.util.Calendar.YEAR;

public class Random {
    private static GregorianCalendar calendar = new GregorianCalendar();

    public static int between(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    public static DateTime date() {
        calendar.set(YEAR, between(1930, 2013));
        calendar.set(DAY_OF_YEAR, between(1, calendar.getActualMaximum(DAY_OF_YEAR)));
        return new DateTime(calendar.getTime());
    }
}
