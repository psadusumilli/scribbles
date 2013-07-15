package com.vijayrc.scribbles.radio.repository;

import com.vijayrc.scribbles.radio.documents.Location;
import lombok.extern.log4j.Log4j;
import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult;
import org.ektorp.support.View;
import org.ektorp.support.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Views({
        @View(name = "find_by_country_state_city",
                map = "function(doc){if(doc.type === 'Location'){emit([doc.country, doc.state, doc.city], doc);}}"),
        @View(name = "count_by_country_state_city",
                map = "function(doc){if(doc.type === 'Location'){emit([doc.country, doc.state, doc.city],1);}}",
                reduce = "function(key, values, rereduce){if(rereduce){return sum(values);}return sum(values);}")
})
@Log4j
public class AllLocations extends BaseRepo<Location> {
    @Autowired
    public AllLocations(CouchDbConnector db) {
        super(Location.class, db);
    }

    public Location findByCountryStateAndCity(String country, String state, String city) {
        ComplexKey key = ComplexKey.of(country, state, city);
        return singleResult(queryView("find_by_country_state_city", key));
    }

    public List<Location> findByRange(Object[] startKey, Object[] endKey) {
        ViewQuery viewQuery = createQuery("find_by_country_state_city").startKey(startKey).endKey(endKey);
        return db.queryView(viewQuery, Location.class);
    }

    public int countByRange(Object[] startKey, Object[] endKey) {
        ViewQuery viewQuery = createQuery("count_by_country_state_city").startKey(startKey).endKey(endKey);
        List<ViewResult.Row> rows = db.queryView(viewQuery).getRows();
        if (rows.isEmpty())
            return 0;
        return rows.get(0).getValueAsInt();
    }

    public Map<String, String> statesCountByCountry() {
        return countBy("count_by_country_state_city", 1);
    }

    public Map<String, String> citiesCountByStateAndCountry() {
        return countBy("count_by_country_state_city", 2);
    }

}
