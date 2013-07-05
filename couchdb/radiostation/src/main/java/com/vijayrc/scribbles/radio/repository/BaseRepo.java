package com.vijayrc.scribbles.radio.repository;

import com.vijayrc.scribbles.radio.domain.BaseDoc;
import lombok.extern.log4j.Log4j;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j
public abstract class BaseRepo<T extends BaseDoc> extends CouchDbRepositorySupport<T> {

    private Class<T> type;

    @Autowired
    protected BaseRepo(Class<T> type, CouchDbConnector db) {
        super(type, db);
        this.type = type;
        initStandardDesignDocument();
    }


}
