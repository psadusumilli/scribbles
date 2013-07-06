package com.vijayrc.scribbles.radio.documents;

import lombok.extern.log4j.Log4j;
import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.joda.time.DateTime;

@Log4j
@TypeDiscriminator("doc.type === 'Subscriber'")
public class Subscriber extends BaseDoc {
    @JsonProperty
    private String subscriberId;
    @JsonProperty
    private DateTime registeredDate;

}
