package com.vijayrc.scribbles.radio.domain;

import com.vijayrc.scribbles.radio.dimension.Time;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.joda.time.DateTime;

@Log4j
@Getter
@Setter
@NoArgsConstructor
@TypeDiscriminator("doc.type === 'Album'")
public class Album extends Doc {
    @JsonProperty
    private String uniqueId;
    @JsonProperty
    private String name;
    @JsonProperty
    private String artistId;
    @JsonProperty
    private Time time;

    public Album(String name, Artist artist, DateTime releaseDate) {
        this.name = name;
        this.artistId = artist.getId();
        this.time = new Time(releaseDate);
        this.uniqueId = name+"|"+artist;
    }

    @Override
    public String toString() {
        return uniqueId;
    }

    @Override
    public boolean equals(Object obj) {
        return this.uniqueId.equals(((Album)obj).uniqueId);
    }
}

