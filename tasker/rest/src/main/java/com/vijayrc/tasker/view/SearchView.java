package com.vijayrc.tasker.view;

import com.vijayrc.meta.Bean;
import com.vijayrc.meta.ToString;
import com.vijayrc.tasker.domain.Key;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@ToString
@Bean
@ApiModel("a search result returned")
public class SearchView {
    @ApiModelProperty(value = "title of the search result", required = true)
    private String title;
    @ApiModelProperty(value = "uri to fetch the search result", required = true)
    private String uri;

    public static SearchView map(Key key){
        SearchView view = new SearchView();
        view.title = key.title();
        view.uri = key.uri();
        return view;
    }
}
