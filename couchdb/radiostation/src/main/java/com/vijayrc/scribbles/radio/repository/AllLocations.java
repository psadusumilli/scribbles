package com.vijayrc.scribbles.radio.repository;

import com.vijayrc.scribbles.radio.documents.Location;
import org.ektorp.CouchDbConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AllLocations extends BaseRepo<Location>{

    @Autowired
    protected AllLocations(CouchDbConnector db) {
        super(Location.class, db);
    }
}
