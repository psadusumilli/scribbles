package com.vijayrc.tasker.domain;

import lombok.ToString;

import java.util.Date;

@ToString
public class Task {
    private String id;
    private String title;
    private String summary;
    private Date startBy;
    private Date endBy;

    public Task(String id, String title, String summary, Date startBy, Date endBy) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.startBy = startBy;
        this.endBy = endBy;
    }

    public String id() {
        return id;
    }
    public String title() {
        return title;
    }
    public String startBy(){
        return startBy != null ? startBy.toString():"";
    }
    public String endBy(){
        return endBy != null ? endBy.toString():"";
    }
    public String summary(){
        return summary;
    }
}
