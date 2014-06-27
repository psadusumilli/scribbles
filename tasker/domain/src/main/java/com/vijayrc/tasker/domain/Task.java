package com.vijayrc.tasker.domain;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;

@XmlRootElement
public class Task {
    private String title;
    private String summary;
    private LocalDateTime startBy;
    private LocalDateTime endBy;

    public static Task create(String title, String summary, LocalDateTime startBy, LocalDateTime endBy) {
        Task task = new Task();
        task.title = title;
        task.summary = summary;
        task.startBy = startBy;
        task.endBy = endBy;
        return task;
    }
}
