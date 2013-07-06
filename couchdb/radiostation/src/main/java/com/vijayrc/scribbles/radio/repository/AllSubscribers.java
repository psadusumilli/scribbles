package com.vijayrc.scribbles.radio.repository;

import com.vijayrc.scribbles.radio.data.DataSetup;
import com.vijayrc.scribbles.radio.documents.Subscriber;
import org.ektorp.CouchDbConnector;
import org.springframework.beans.factory.annotation.Autowired;

public class AllSubscribers extends BaseRepo<Subscriber>{
    @Autowired
    protected AllSubscribers(CouchDbConnector db) {
        super(Subscriber.class, db);
    }

    @DataSetup(order = 2, description = "subscribers setup")
    public void addData() {
    }

}
