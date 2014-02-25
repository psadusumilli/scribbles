package com.vijayrc.java8;

import org.junit.Test;

import java.time.*;

import static com.vijayrc.java8.Log.print;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DateTimeTest {

    @Test
    public void shouldPlayWithDates(){
        LocalDate d1 = LocalDate.now();
        LocalDate d2 = LocalDate.of(2014, 2, 15).plusDays(10);
        print(d1);
        print(d2);

        LocalDateTime d3 = d2.atTime(13, 23, 34, 45);
        assertThat(d3.toString(), is("2014-02-25T13:23:34.000000045"));
        LocalDateTime d4 = d3.plus(Period.of(1, 2, 3));
        assertThat(d4.toString(), is("2015-04-28T13:23:34.000000045"));
    }

    @Test
    public void shouldPlayWithClocks(){
        Clock c1 = Clock.systemDefaultZone();
        print(c1.getZone() + "|" + c1.instant());

        StringBuilder builder = new StringBuilder();
        for (String zoneId : ZoneId.getAvailableZoneIds())
            builder.append(zoneId).append(",");
        print(builder);

        ZoneId z1 = ZoneId.of("Australia/Tasmania");
        Clock c2 = c1.withZone(z1);
        print(c2.instant().atZone(z1));

        LocalDate d1 = LocalDate.now(c1);
        LocalDate d2 = LocalDate.now(c2);
        print(d1 + "|" + d2);

    }
}
