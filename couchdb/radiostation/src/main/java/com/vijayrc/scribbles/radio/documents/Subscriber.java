package com.vijayrc.scribbles.radio.documents;

import com.vijayrc.scribbles.radio.vo.Time;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.joda.time.DateTime;

@Log4j
@TypeDiscriminator("doc.type === 'Subscriber'")
@NoArgsConstructor
@Getter
public class Subscriber extends BaseDoc {
    @JsonProperty
    private String subscriberId;
    @JsonProperty
    private Time time;
    @JsonProperty
    private Location location;

    public Subscriber(String subscriberId, DateTime registeredDate, Location location) {
        this.subscriberId = subscriberId;
        this.location = location;
        this.time = new Time(registeredDate);
    }

    @Override
    public String toString() {
        return subscriberId+"|"+time;
    }
}
