package com.vijayrc.scribbles.radio.repository;

import com.vijayrc.scribbles.radio.documents.Play;
import com.vijayrc.scribbles.radio.vo.Time;
import lombok.extern.log4j.Log4j;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult;
import org.ektorp.support.View;
import org.ektorp.support.Views;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static ch.lambdaj.Lambda.*;

@Repository
@Log4j
@Views({
        @View(name = "songs_count_by_time",
                map = "function(doc){if(doc.type === 'Play'){emit([doc.time.year,doc.time.month,doc.time.day,doc.songId],1);}}",
                reduce = "function(key,values,rereduce){if(rereduce){return sum(values);}return sum(values);}"),
        @View(name = "find_by_time",
                map = "function(doc){if(doc.type === 'Play'){emit([doc.time.year,doc.time.month,doc.time.day,doc.time.hour], doc.songId);}}")
})
public class AllPlays extends BaseRepo<Play> {

    private AllSongs allSongs;

    @Autowired
    public AllPlays(CouchDbConnector db, AllSongs allSongs) {
        super(Play.class, db);
        this.allSongs = allSongs;
    }

    public void findPopularSongForDay(DateTime dateTime) {
        Time time = new Time(dateTime);
        Object[] key = new Object[]{time.getYear(), time.getMonth(), time.getDay()};
        ViewQuery viewQuery = createQuery("songs_count_by_time").includeDocs(false).group(true).groupLevel(3).key(key);
        ViewResult viewResult = db.queryView(viewQuery);
    }

    public List<String> findSongsPlayedByTimeRange(Time startTime, Time endTime) {
        ViewQuery viewQuery = createQuery("find_by_time").startKey(startTime.asArrayKey()).endKey(endTime.asArrayKey());
        List<ViewResult.Row> rows = db.queryView(viewQuery).getRows();
        return extract(rows, on(ViewResult.Row.class).getValue());
    }
}

