package com.vijayrc.scribbles.radio.repository;

import com.vijayrc.scribbles.radio.domain.Song;
import lombok.extern.log4j.Log4j;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.GenerateView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
}
