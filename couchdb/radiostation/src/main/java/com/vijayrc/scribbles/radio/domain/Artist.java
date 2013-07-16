package com.vijayrc.scribbles.radio.domain;

import com.vijayrc.scribbles.radio.dimension.Location;
import com.vijayrc.scribbles.radio.dimension.Time;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.docref.CascadeType;
import org.ektorp.docref.DocumentReferences;
import org.ektorp.docref.FetchType;
import org.ektorp.support.TypeDiscriminator;
import org.joda.time.DateTime;

import java.util.HashSet;
import java.util.Set;

@Log4j
@Getter
@Setter
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
    private Location location;
    @DocumentReferences(backReference = "artistId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Album> albums = new HashSet<Album>();

    public Artist(String name, DateTime dob, Location location) {
        this.name = name;
        this.location = location;
        this.time = new Time(dob);
        this.artistId = this.name + "|" + this.time;
    }

    @Override
    public String toString() {
        return artistId;
    }

    public void add(Album album) {
        album.setArtistId(this.getId());
        albums.add(album);
    }
}
