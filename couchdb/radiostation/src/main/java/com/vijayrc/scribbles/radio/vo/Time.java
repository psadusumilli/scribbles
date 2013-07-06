package com.vijayrc.scribbles.radio.vo;

import org.codehaus.jackson.annotate.JsonProperty;

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
}
