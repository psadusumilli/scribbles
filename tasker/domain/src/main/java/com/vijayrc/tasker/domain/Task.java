package com.vijayrc.tasker.domain;

import com.vijayrc.meta.NoArgsConstr;
import com.vijayrc.meta.ToString;

@ToString
@NoArgsConstr
public class Task {
    private int card;
    private String description;
    private TaskStatus status;

    public static Task create(int card, String description, String status) {
        Task task = new Task();
        task.card = card;
        task.description = description;
        task.status = TaskStatus.valueOf(status);
        return task;
    }

    public String description(){
        return description;
    }
    public String status() {
        return status.toString();
    }
    public String card() {
        return String.valueOf(card);
    }
}
