package com.vijayrc.tasker.view;

import com.vijayrc.meta.Bean;
import com.vijayrc.meta.ToString;
import com.vijayrc.tasker.domain.Key;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@ToString
@Bean
public class SearchView {
    private String title;
    private String uri;

    public static SearchView map(Key key){
        SearchView view = new SearchView();
        view.title = key.title();
        view.uri = key.uri();
        return view;
    }
}
