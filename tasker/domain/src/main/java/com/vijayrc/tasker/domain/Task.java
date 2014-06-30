package com.vijayrc.tasker.domain;

import java.util.Date;
import java.util.UUID;

public class Task {
    private String id;
    private String title;
    private String summary;
    private Date startBy;
    private Date endBy;

    public Task() {
    }

    public Task(String id, String title, String summary, Date startBy, Date endBy) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.startBy = startBy;
        this.endBy = endBy;
    }
    public static Task create(String title, String summary, Date startBy, Date endBy) {
        return new Task(UUID.randomUUID().toString(),title,summary,startBy,endBy);
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
    public boolean hasId(String id){
        return this.id.equals(id);
    }
    @Override
    public String toString() {
        return "Task["+id+"|"+title+"]";
    }

}
