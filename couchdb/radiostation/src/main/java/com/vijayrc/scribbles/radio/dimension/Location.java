package com.vijayrc.scribbles.radio.dimension;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonProperty;


@NoArgsConstructor
@Getter
public class Location{
    @JsonProperty
    private String city;
    @JsonProperty
    private String state;
    @JsonProperty
    private String country;

    public Location(String city, String state, String country) {
        this.city = city;
        this.state = state;
        this.country = country;
    }

    @Override
    public String toString() {
        return "["+country+"|"+state+"|"+city+"]";
    }
}
