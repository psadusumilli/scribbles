package com.vijayrc.scribbles.radio.vo;
import com.vijayrc.scribbles.radio.documents.BaseDoc;
import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.joda.time.*;

@TypeDiscriminator("doc.type === 'Artist'")
public class Artist extends BaseDoc{
    @JsonProperty
    private String artistId;
    @JsonProperty
    private String name;
    @JsonProperty
    private DateTime dob;
    @JsonProperty
    private String history;

    public Artist(String name, DateTime dob) {
        this.name = name;
        this.dob = dob;
        this.artistId = name+"-"+dob;
    }

    public void history(String history){
        this.history = history;
    }
}
