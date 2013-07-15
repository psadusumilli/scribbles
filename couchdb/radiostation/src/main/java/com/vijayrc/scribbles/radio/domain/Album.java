package com.vijayrc.scribbles.radio.domain;

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
@TypeDiscriminator("doc.type === 'Album'")
public class Album extends Doc {
    @JsonProperty
    private String albumId;
    @JsonProperty
    private String name;
    @JsonProperty
    private Artist artist;
    @JsonProperty
    private Time time;

    public Album(String name, Artist artist, DateTime releaseDate) {
        this.name = name;
        this.artist = artist;
        this.time = new Time(releaseDate);
        this.albumId = name+"|"+artist;
    }

    @Override
    public String toString() {
        return albumId;
    }
}

