package com.vijayrc.tasker.service;

import com.vijayrc.tasker.repository.AllTasks;
import com.vijayrc.tasker.view.TaskView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
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
}
