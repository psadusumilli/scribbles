package com.vijayrc.scribbles.radio.documents;

import com.vijayrc.scribbles.radio.vo.Time;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.joda.time.DateTime;

@TypeDiscriminator("doc.type === 'Play'")
@NoArgsConstructor
@Getter
public class Play extends BaseDoc {
    @JsonProperty
    private String songId;
    @JsonProperty
    private String subscriberId;
    @JsonProperty
    private Time time;

    public Play(String songId, String subscriberId, DateTime dateTime) {
        this.songId = songId;
        this.subscriberId = subscriberId;
        this.time = new Time(dateTime);
    }

    @Override
    public String toString() {
        return songId + "-" + subscriberId + "-" + time;
    }
}