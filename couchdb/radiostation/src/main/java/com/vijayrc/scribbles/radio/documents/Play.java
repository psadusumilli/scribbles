package com.vijayrc.scribbles.radio.documents;

import com.vijayrc.scribbles.radio.vo.Time;
import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;

@TypeDiscriminator("doc.type === 'Play'")
public class Play extends BaseDoc{
    @JsonProperty
    private String songId;
    @JsonProperty
    private String subscriberId;
    @JsonProperty
    private Time time;

}