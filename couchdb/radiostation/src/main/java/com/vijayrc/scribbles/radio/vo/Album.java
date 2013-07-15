package com.vijayrc.scribbles.radio.vo;

import com.vijayrc.scribbles.radio.documents.Artist;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;

@NoArgsConstructor
public class Album {
    @JsonProperty
    private String title;
    @JsonProperty
    private Artist artist;
    @JsonProperty
    private DateTime releaseDate;

    public Album(String title, Artist artist, DateTime releaseDate) {
        this.title = title;
        this.artist = artist;
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return title+"|"+artist;
    }
}

