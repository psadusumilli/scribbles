package com.vijayrc.scribbles.radio.repository;

import com.google.gson.Gson;
import com.vijayrc.scribbles.radio.aggregate.PlayHistory;
import com.vijayrc.scribbles.radio.dimension.Location;
import com.vijayrc.scribbles.radio.dimension.Time;
import com.vijayrc.scribbles.radio.domain.Play;
import com.vijayrc.scribbles.radio.domain.Subscriber;
import lombok.extern.log4j.Log4j;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.ektorp.support.Views;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Views({
        @View(name = "find_by_registered_time",
                map = "function(doc){if(doc.type === 'Subscriber'){emit([doc.time.year,doc.time.month,doc.time.day, doc.time.hour],doc)}}"),
        @View(name = "find_by_registered_location",
                map = "function(doc){if(doc.type === 'Subscriber'){emit([doc.location.country,doc.location.state,doc.location.city],doc)}}"),
        @View(name = "count_by_registered_time",
                map = "function(doc){if(doc.type === 'Subscriber'){emit([doc.time.year,doc.time.month,doc.time.day, doc.time.hour],1)}}",
                reduce = "function(key, values, rereduce){if(rereduce){return sum(values);}return sum(values);}"),
        @View(name = "count_by_registered_location",
                map = "function(doc){if(doc.type === 'Subscriber'){emit([doc.location.country,doc.location.state,doc.location.city],1)}}",
                reduce = "function(key, values, rereduce){if(rereduce){return sum(values);}return sum(values);}"),
        @View(name = "find_playhistory",
                map = "function(doc) {if(doc.type == 'Subscriber') {emit([doc.subscriberId, 0], doc);} else if(doc.type == 'Play') {emit([doc.subscriberId, 1], doc);}}")
})
@Log4j
public class AllSubscribers extends Repo<Subscriber> {
    @Autowired

    public AllSubscribers(CouchDbConnector db) {
        super(Subscriber.class, db);
    }

    @GenerateView
    public Subscriber findBySubscriberId(String subscriberId) {
        return singleResult(queryView("by_subscriberId", subscriberId));
    }

    public List<Subscriber> findByDateRange(DateTime startTime, DateTime endTime) {
        ViewQuery viewQuery = createQuery("find_by_registered_time")
                .startKey(new Time(startTime).asArrayKey())
                .endKey(new Time(endTime).asArrayKey());
        return db.queryView(viewQuery, Subscriber.class);
    }

    public List<Subscriber> findByLocationRange(Location location1, Location location2) {
        ViewQuery viewQuery = createQuery("find_by_registered_location")
                .startKey(location1.asArrayKey())
                .endKey(location2.asArrayKey());
        return db.queryView(viewQuery, Subscriber.class);
    }

    public int countByDateRange(DateTime startTime, DateTime endTime) {
        ViewQuery viewQuery = createQuery("count_by_registered_time")
                .startKey(new Time(startTime).asArrayKey())
                .endKey(new Time(endTime).asArrayKey());
        List<ViewResult.Row> rows = db.queryView(viewQuery).getRows();
        return countResult(rows);
    }

    public int countByLocationRange(Location location1, Location location2) {
        ViewQuery viewQuery = createQuery("count_by_registered_location")
                .startKey(location1.asArrayKey())
                .endKey(location2.asArrayKey()).includeDocs(false).group(true);
        List<ViewResult.Row> rows = db.queryView(viewQuery).getRows();
        return countResult(rows);
    }

    public Map<String, String> countByCountry() {
        return countBy("count_by_registered_location", 1);
    }

    public Map<String, String> countByState() {
        return countBy("count_by_registered_location", 2);
    }

    private int countResult(List<ViewResult.Row> rows) {
        if (rows.isEmpty())
            return 0;
        return rows.get(0).getValueAsInt();
    }

    public PlayHistory findPlayHistory(String subscriberId) {
        ViewQuery viewQuery = createQuery("find_playhistory")
                .startKey(new Object[]{subscriberId})
                .endKey(new Object[]{subscriberId, 2});

        ViewResult viewResult = db.queryView(viewQuery);
        if (viewResult == null || viewResult.isEmpty()) return null;

        PlayHistory playHistory = new PlayHistory();
        Gson gson = new Gson();
        for (ViewResult.Row row : viewResult.getRows()) {
            if (playHistory.hasNoSubscriber())
                playHistory.setSubscriber(gson.fromJson(row.getValue(), Subscriber.class));
            playHistory.addPlay(gson.fromJson(row.getValue(), Play.class));
        }
        return playHistory;
    }

}
