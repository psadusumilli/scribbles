package com.vijayrc.scribbles.radio.documents;

import com.vijayrc.scribbles.radio.vo.Album;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;

@TypeDiscriminator("doc.type === 'Song'")
@NoArgsConstructor
@Getter
public class Song extends BaseDoc {
    @JsonProperty
    private String title;
    @JsonProperty
    private Album album;
    @JsonProperty
    private String genre;
    @JsonProperty
    private Integer duration;
    @JsonProperty
    private String songId;

    public Song(String title, Album album, String genre, Integer duration) {
        this.title = title;
        this.album = album;
        this.genre = genre;
        this.duration = duration;
        this.songId = title+"|"+album;
    }

    @Override
    public String toString() {
      return songId;
    }
}
