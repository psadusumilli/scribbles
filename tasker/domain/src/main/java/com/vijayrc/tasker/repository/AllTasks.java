package com.vijayrc.tasker.repository;

import com.vijayrc.tasker.domain.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AllTasks {
    private static Logger log = LogManager.getLogger(AllTasks.class);
    private List<Task> tasks = new ArrayList<>();

    public AllTasks() {
        LocalDateTime now = LocalDateTime.now();
        for(int i = 0;i<10;i++) {
            tasks.add(Task.create("task-"+i, "summary-"+i, now,now.plusDays(i)));
            now = now.plusDays(i);
        }
    }

    public List<Task> all(){
       log.info("return tasks size:"+tasks.size());
        System.out.println(tasks.size());
       return tasks;
    }
}
