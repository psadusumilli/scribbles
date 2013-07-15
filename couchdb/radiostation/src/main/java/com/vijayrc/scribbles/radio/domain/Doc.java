package com.vijayrc.scribbles.radio.domain;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.CouchDbDocument;

public abstract class Doc extends CouchDbDocument{
    @JsonProperty
    private String type;

    protected Doc() {
        this.type = this.getClass().getSimpleName();
    }
}
