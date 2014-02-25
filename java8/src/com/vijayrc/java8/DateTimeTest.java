package com.vijayrc.java8;

import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DateTimeTest {

    @Test
    public void shouldPlayWithTime(){
        LocalDate today = LocalDate.now();
        assertThat(today.toString(),is("2014-02-25"));

    }
}
