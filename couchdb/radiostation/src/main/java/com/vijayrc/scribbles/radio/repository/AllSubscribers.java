package com.vijayrc.scribbles.radio.repository;

import com.google.gson.Gson;
import com.vijayrc.scribbles.radio.documents.Play;
import com.vijayrc.scribbles.radio.documents.PlayHistory;
import com.vijayrc.scribbles.radio.documents.Subscriber;
import lombok.extern.log4j.Log4j;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.ektorp.support.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Views({
        @View(name = "find_all_registered_between_two_dates", map = "function(doc){if(doc.type === 'Subscriber'){emit([doc.time.year,doc.time.month,doc.time.day, doc.time.hour],doc)}}"),
        @View(name = "find_playhistory", map = "function(doc) {if(doc.type == 'Subscriber') {emit([doc.subscriberId, 0], doc);} else if(doc.type == 'Play') {emit([doc.subscriberId, 1], doc);}}")
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

    public List<Subscriber> findByRegisteredDateRange(Object[] startKey, Object[] endKey) {
        ViewQuery viewQuery = createQuery("find_all_registered_between_two_dates").startKey(startKey).endKey(endKey);
        return db.queryView(viewQuery, Subscriber.class);
    }

    public PlayHistory findPlayHistory(String subscriberId) {
        Object[] startKey = new Object[]{subscriberId};
        Object[] endKey = new Object[]{subscriberId, 2};
        ViewQuery viewQuery = createQuery("find_playhistory").startKey(startKey).endKey(endKey);
        ViewResult viewResult = db.queryView(viewQuery);
        if(viewResult == null || viewResult.isEmpty()) return null;

        PlayHistory playHistory = new PlayHistory();
        Gson gson = new Gson();
        for (ViewResult.Row row : viewResult.getRows()){
            if(playHistory.hasNoSubscriber())
                  playHistory.setSubscriber(gson.fromJson(row.getValue(),Subscriber.class));
            playHistory.addPlay(gson.fromJson(row.getValue(),Play.class));
        }
        return playHistory;
    }

}
