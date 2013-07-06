package com.vijayrc.scribbles.radio.repository;

import com.vijayrc.scribbles.radio.data.DataSetup;
import com.vijayrc.scribbles.radio.documents.Artist;
import lombok.extern.log4j.Log4j;
import org.ektorp.CouchDbConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("singleton")
@Log4j
public class AllArtists extends BaseRepo<Artist> {
    @Autowired
    private AllDates allDates;

    @Autowired
    protected AllArtists(CouchDbConnector db) {
        super(Artist.class, db);
    }

    @DataSetup(order = 2, description = "artists setup")
    public void addData() {
        int artists = 10;
        for (int i = 1; i <= artists; i++)
            add(new Artist("artist_" + i, allDates.random()));
    }
}
