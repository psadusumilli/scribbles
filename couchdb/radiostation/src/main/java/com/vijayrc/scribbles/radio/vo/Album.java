package com.vijayrc.scribbles.radio.vo;

import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;

public class Album {
    @JsonProperty
    private String title;
    @JsonProperty
    private Artist artist;
    @JsonProperty
    private DateTime releaseDate;
    @JsonProperty
    private Integer duration;

}

