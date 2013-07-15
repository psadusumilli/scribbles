package com.vijayrc.scribbles.radio.repository;

import com.vijayrc.scribbles.radio.domain.Artist;
import lombok.extern.log4j.Log4j;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.GenerateView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Log4j
public class AllArtists extends Repo<Artist> {
    @Autowired
    public AllArtists(CouchDbConnector db) {
        super(Artist.class, db);
    }

    @GenerateView
    public Artist findByName(String name) {
        return singleResult(queryView("by_name", name));
    }

}
