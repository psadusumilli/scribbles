package com.vijayrc.scribbles.radio.vo;
import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.*;

public class Artist {
    @JsonProperty
    private String name;
    @JsonProperty
    private DateTime dob;
    @JsonProperty
    private String city;
    @JsonProperty
    private String details;

}
