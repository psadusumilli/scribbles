package com.vijayrc.scribbles.radio.repository;

import com.vijayrc.scribbles.radio.documents.BaseDoc;
import lombok.extern.log4j.Log4j;
import org.ektorp.BulkDeleteDocument;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.GenerateView;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Log4j
public abstract class BaseRepo<T extends BaseDoc> extends CouchDbRepositorySupport<T> {

    private Class<T> type;

    @Autowired
    protected BaseRepo(Class<T> type, CouchDbConnector db) {
        super(type, db);
        this.type = type;
        initStandardDesignDocument();
    }

    @Override
    @GenerateView
    public List<T> getAll() {
        return super.getAll();
    }

    public List<T> getAll(int limit) {
        ViewQuery q = createQuery("all").limit(limit).includeDocs(true);
        return db.queryView(q, type);
    }

    protected T singleResult(List<T> resultSet) {
        return (resultSet == null || resultSet.isEmpty()) ? null : resultSet.get(0);
    }

    public void removeAll() {
        removeAll(getAll());
    }

    private void removeAll(List<T> entities) {
        List<BulkDeleteDocument> bulkDeleteQueue = new ArrayList<BulkDeleteDocument>(entities.size());
        for (T entity : entities)
            bulkDeleteQueue.add(BulkDeleteDocument.of(entity));
        db.executeBulk(bulkDeleteQueue);
    }

}
