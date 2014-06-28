package com.vijayrc.tasker.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Task {
    private UUID id;
    private String title;
    private String summary;
    private LocalDateTime startBy;
    private LocalDateTime endBy;

    public static Task create(String title, String summary, LocalDateTime startBy, LocalDateTime endBy) {
        Task task = new Task();
        task.id = UUID.randomUUID();
        task.title = title;
        task.summary = summary;
        task.startBy = startBy;
        task.endBy = endBy;
        return task;
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
        return this.id.toString().endsWith(id);
    }
    @Override
    public String toString() {
        return "Task["+id+"|"+title+"]";
    }
}
