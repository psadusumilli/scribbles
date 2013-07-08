package com.vijayrc.scribbles.radio.repository;

import com.vijayrc.scribbles.radio.documents.Subscriber;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.GenerateView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AllSubscribers extends BaseRepo<Subscriber> {
    @Autowired
    public AllSubscribers(CouchDbConnector db) {
        super(Subscriber.class, db);
    }

    @GenerateView
    public Subscriber findBySubscriberId(String subscriberId) {
        return singleResult(queryView("by_subscriberId", subscriberId));
    }
}
