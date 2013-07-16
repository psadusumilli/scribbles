package com.vijayrc.scribbles.radio.repository;

import com.vijayrc.scribbles.radio.domain.Song;
import lombok.extern.log4j.Log4j;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.support.GenerateView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Log4j
public class AllSongs extends Repo<Song> {
    @Autowired
    public AllSongs(CouchDbConnector db) {
        super(Song.class, db);
    }

    @GenerateView
    public Song findByName(String name) {
        return singleResult(queryView("by_name", name));
    }

    public List<Song> getAllFor(List<String> ids) {
        ViewQuery q = new ViewQuery().allDocs().includeDocs(true).keys(ids);
        return db.queryView(q, Song.class);
    }
}
