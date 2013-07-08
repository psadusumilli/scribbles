package com.vijayrc.scribbles.radio.repository;

import com.vijayrc.scribbles.radio.documents.Artist;
import com.vijayrc.scribbles.radio.documents.Play;
import lombok.extern.log4j.Log4j;
import org.ektorp.CouchDbConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Log4j
public class AllPlays extends BaseRepo<Play> {
    @Autowired
    public AllPlays(CouchDbConnector db) {
        super(Play.class, db);
    }

}
