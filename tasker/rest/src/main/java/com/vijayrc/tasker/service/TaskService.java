package com.vijayrc.tasker.service;

import com.vijayrc.tasker.repository.AllTasks;
import com.vijayrc.tasker.view.TaskView;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class TaskService {
    @Autowired
    private AllTasks allTasks;

    public List<TaskView> getAll(){
        List<TaskView> views = new ArrayList<>();
        allTasks.all().forEach(t -> views.add(TaskView.createFrom(t)));
        return views;
    }
    public TaskView getFor(String id) {
        return TaskView.createFrom(allTasks.getFor(id));
    }
    public void remove(String id) {
        allTasks.remove(id);
    }
}
