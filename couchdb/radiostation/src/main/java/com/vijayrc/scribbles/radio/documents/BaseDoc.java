package com.vijayrc.scribbles.radio.documents;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.CouchDbDocument;

public abstract class BaseDoc extends CouchDbDocument{

    @JsonProperty
    private String type;

    protected BaseDoc() {
        this.type = this.getClass().getSimpleName();
    }
}
