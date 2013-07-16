package com.vijayrc.scribbles.radio.repository;

import com.vijayrc.scribbles.radio.dimension.Time;
import com.vijayrc.scribbles.radio.domain.Play;
import com.vijayrc.scribbles.radio.domain.Song;
import lombok.extern.log4j.Log4j;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult;
import org.ektorp.support.View;
import org.ektorp.support.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ch.lambdaj.Lambda.extract;
import static ch.lambdaj.Lambda.on;

@Repository
@Log4j
@Views({
        @View(name = "count_songs_by_time",
                map = "function(doc){if(doc.type === 'Play'){emit([doc.time.year,doc.time.month,doc.time.day,doc.songId],1);}}",
                reduce = "function(key,values,rereduce){if(rereduce){return sum(values);}return sum(values);}"),

        @View(name = "find_songs_by_time",
                map = "function(doc){if(doc.type === 'Play'){emit([doc.time.year,doc.time.month,doc.time.day,doc.time.hour], doc.songId);}}")
})
public class AllPlays extends Repo<Play> {

    private AllSongs allSongs;

    @Autowired
    public AllPlays(CouchDbConnector db, AllSongs allSongs) {
        super(Play.class, db);
        this.allSongs = allSongs;
    }

    public Map<String, String> findSongsCountForTimeRange(Time startTime, Time endTime) {
        Object[] startKey = new Object[]{startTime.getYear(), startTime.getMonth(), startTime.getDay()};
        Object[] endKey = new Object[]{endTime.getYear(), endTime.getMonth(), endTime.getDay()};

        ViewQuery viewQuery = createQuery("count_songs_by_time").includeDocs(false).group(true).groupLevel(3)
.startKey(startKey).endKey(endKey);

        List<ViewResult.Row> rows = db.queryView(viewQuery).getRows();
        Map<String, String> map = new HashMap<String, String>();
        for (ViewResult.Row row : rows)
            map.put(row.getKey(), row.getValue());
        return map;
    }

    public List<Song> findSongsPlayedByTimeRange(Time startTime, Time endTime) {
        ViewQuery viewQuery = createQuery("find_songs_by_time").startKey(startTime.asArrayKey()).endKey(endTime.asArrayKey());
        List<String> songsIds = extract(db.queryView(viewQuery).getRows(), on(ViewResult.Row.class).getValue());
        return allSongs.getAllFor(songsIds);
    }
}

