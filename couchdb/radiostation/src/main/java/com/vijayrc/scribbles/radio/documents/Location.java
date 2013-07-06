package com.vijayrc.scribbles.radio.documents;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;

@TypeDiscriminator("doc.type === 'Location'")
public class Location extends BaseDoc {
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
}
