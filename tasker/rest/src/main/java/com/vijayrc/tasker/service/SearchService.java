package com.vijayrc.tasker.service;

import com.vijayrc.tasker.repository.AllKeys;
import com.vijayrc.tasker.view.SearchView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {
    @Autowired
    private AllKeys allKeys;

    public List<SearchView> fetch(String key){
        List<SearchView> views = new ArrayList<>();
        allKeys.fetch(key).forEach(k -> views.add(SearchView.map(k)));
        return views;
    }


}
