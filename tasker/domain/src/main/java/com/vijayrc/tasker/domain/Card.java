package com.vijayrc.tasker.domain;

import com.vijayrc.meta.ToString;

import java.util.Date;

import static org.apache.commons.lang.StringUtils.isNotBlank;

@ToString
public class Card {
    private String id;
    private String title;
    private String summary;
    private Date startBy;
    private Date endBy;

    public Card() {
    }

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
    public void merge(Card card) {
        if(isNotBlank(card.title)) title = card.title;
        if(isNotBlank(card.summary)) summary = card.summary;
        if(card.startBy != null) startBy = card.startBy;
        if(card.endBy != null) endBy = card.endBy;
    }
}
