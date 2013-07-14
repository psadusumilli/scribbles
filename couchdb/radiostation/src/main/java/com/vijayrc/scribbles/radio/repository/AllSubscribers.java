package com.vijayrc.scribbles.radio.repository;

import com.vijayrc.scribbles.radio.documents.Location;
import com.vijayrc.scribbles.radio.documents.Subscriber;
import lombok.extern.log4j.Log4j;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.ektorp.support.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Views({
        @View(name = "find_all_registered_between_two_dates", map = "function(doc){if(doc.type === 'Subscriber'){emit([doc.time.year,doc.time.month,doc.time.day, doc.time.hour],doc)}}")
})

@Log4j
public class AllSubscribers extends BaseRepo<Subscriber> {
    @Autowired
    public AllSubscribers(CouchDbConnector db) {
        super(Subscriber.class, db);
    }

    @GenerateView
    public Subscriber findBySubscriberId(String subscriberId) {
        return singleResult(queryView("by_subscriberId", subscriberId));
    }

    public List<Subscriber> findByRange(Object[] startKey, Object[] endKey) {
        ViewQuery viewQuery = createQuery("find_all_registered_between_two_dates").startKey(startKey).endKey(endKey);
        return db.queryView(viewQuery, Subscriber.class);
    }
}
