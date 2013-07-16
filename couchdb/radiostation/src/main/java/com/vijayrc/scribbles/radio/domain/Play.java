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
@TypeDiscriminator("doc.type === 'Play'")
public class Play extends Doc {
    @JsonProperty
    private String songId;
    @JsonProperty
    private String subscriberId;
    @JsonProperty
    private Time time;
    @JsonProperty
    private Location location;

    public Play(Song song, Subscriber subscriber, DateTime dateTime) {
        this.songId = song.getId();
        this.subscriberId = subscriber.getId();
        this.location = subscriber.getLocation();
        this.time = new Time(dateTime);
    }

    @Override
    public String toString() {
        return songId + "|" + subscriberId + "|" + time;
    }
}