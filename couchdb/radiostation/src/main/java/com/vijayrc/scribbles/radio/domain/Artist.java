package com.vijayrc.scribbles.radio.domain;

import com.vijayrc.scribbles.radio.dimension.Location;
import com.vijayrc.scribbles.radio.dimension.Time;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.joda.time.DateTime;

@Log4j
@Getter
@NoArgsConstructor
@TypeDiscriminator("doc.type === 'Artist'")
public class Artist extends Doc {
    @JsonProperty
    private String artistId;
    @JsonProperty
    private String name;
    @JsonProperty
    private Time time;
    @JsonProperty
    private String history;
    @JsonProperty
    private Location location;

    public Artist(String name, DateTime dob, Location location) {
        this.name = name;
        this.location = location;
        this.time = new Time(dob);
        this.artistId = this.name+"|"+ this.time;
    }

    public void history(String history){
        this.history = history;
    }

    @Override
    public String toString() {
        return artistId;
    }
}
