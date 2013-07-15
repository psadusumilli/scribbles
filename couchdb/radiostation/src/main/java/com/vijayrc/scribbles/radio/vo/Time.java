package com.vijayrc.scribbles.radio.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;

@Getter
@NoArgsConstructor
public class Time {
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
        return "["+year+"/"+month+"/"+day +" "+hour+":"+minute+"]";
    }

    public Object[] asArrayKey() {
        return new Object[]{year, month, day, hour};
    }
}
