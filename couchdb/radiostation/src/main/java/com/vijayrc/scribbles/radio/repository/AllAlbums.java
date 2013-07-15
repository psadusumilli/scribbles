package com.vijayrc.scribbles.radio.repository;

import com.vijayrc.scribbles.radio.domain.Album;
import lombok.extern.log4j.Log4j;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.GenerateView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Log4j
public class AllAlbums extends Repo<Album> {

    @Autowired
    public AllAlbums(CouchDbConnector db) {
        super(Album.class, db);
    }

    @GenerateView
    public Album findByName(String name) {
        return singleResult(queryView("by_name", name));
    }

}
