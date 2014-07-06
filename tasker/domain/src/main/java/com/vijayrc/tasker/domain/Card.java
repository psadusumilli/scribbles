package com.vijayrc.tasker.domain;

import com.vijayrc.meta.NoArgsConstr;
import com.vijayrc.meta.ToString;

import java.util.Date;

@ToString
@NoArgsConstr
public class Card {
    private String id;
    private String title;
    private String summary;
    private Date startBy;
    private Date endBy;

    public Card(String id, String title, String summary, Date startBy, Date endBy) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.startBy = startBy;
        this.endBy = endBy;
    }
    public Card(String title, String summary, Date startBy, Date endBy){
        this(null,title,summary,startBy,endBy);
    }
    public String id() {
        return id;
    }
    public String title() {
        return title;
    }
    public Date startBy(){
        return startBy;
    }
    public Date endBy(){
        return endBy;
    }
    public String summary(){
        return summary;
    }

}
