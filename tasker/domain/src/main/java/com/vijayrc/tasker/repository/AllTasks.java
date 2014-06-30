package com.vijayrc.tasker.repository;

import com.vijayrc.tasker.domain.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@Scope("singleton")
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
       log.info("tasks size:" + tasks.size());
       return tasks;
    }
    public Task getFor(String id) {
        log.info("fetching for: "+id);
        Task task = tasks.stream().filter(t -> t.hasId(id)).findFirst().get();
        log.info("task: "+task);
        return task;
    }
}
