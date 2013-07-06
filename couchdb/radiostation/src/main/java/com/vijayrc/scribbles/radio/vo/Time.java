package com.vijayrc.scribbles.radio.vo;

import lombok.Getter;
import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;

@Getter
public class Time{
    @JsonProperty
    private int minute;
    @JsonProperty
    private int hour;
    @JsonProperty
    private int day;
    @JsonProperty
    private int month;
    @JsonProperty
    private int year;

    public Time(DateTime dateTime) {
        year = dateTime.getYear();
        month = dateTime.getMonthOfYear();
        day = dateTime.getDayOfMonth();
        hour = dateTime.getHourOfDay();
        minute = dateTime.getMinuteOfHour();
    }

    @Override
    public String toString() {
        return minute+":"+hour+":"+day+":"+minute+":"+year;
    }
}
