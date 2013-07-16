package com.vijayrc.scribbles.radio.dimension;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonProperty;


@NoArgsConstructor
@Getter
public class Location {
    @JsonProperty
    private String city;
    @JsonProperty
    private String state;
    @JsonProperty
    private String country;

    public Location(String country, String state, String city) {
        this.city = city;
        this.state = state;
        this.country = country;
    }

    @Override
    public String toString() {
        return "[" + country + "|" + state + "|" + city + "]";
    }

    public Object[] asArrayKey() {
        return new Object[]{country, state, city};
    }
}
