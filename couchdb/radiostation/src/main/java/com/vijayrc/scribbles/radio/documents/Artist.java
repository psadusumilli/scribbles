package com.vijayrc.scribbles.radio.documents;

import com.vijayrc.scribbles.radio.vo.Time;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.joda.time.DateTime;

@TypeDiscriminator("doc.type === 'Artist'")
@Getter
@NoArgsConstructor
public class Artist extends BaseDoc{
    @JsonProperty
    private String artistId;
    @JsonProperty
    private String name;
    @JsonProperty
    private Time dob;
    @JsonProperty
    private String history;

    public Artist(String name, DateTime dob) {
        this.name = name;
        this.dob = new Time(dob);
        this.artistId = this.name+"-"+ this.dob;
    }

    public void history(String history){
        this.history = history;
    }

    @Override
    public String toString() {
        return artistId;
    }
}
