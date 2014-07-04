package com.vijayrc.tasker.domain;

import com.vijayrc.meta.NoArgsConstr;
import com.vijayrc.meta.ToString;

@ToString
@NoArgsConstr
public class Task {
    private int card;
    private String description;
    private TaskStatus status;

    public String description(){
        return description;
    }
}
