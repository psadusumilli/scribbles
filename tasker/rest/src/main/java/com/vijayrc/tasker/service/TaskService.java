package com.vijayrc.tasker.service;

import com.vijayrc.tasker.repository.AllTasks;
import com.vijayrc.tasker.view.TaskView;

import java.util.ArrayList;
import java.util.List;

public class TaskService {
    private AllTasks allTasks = new AllTasks();

    public List<TaskView> getAll(){
        List<TaskView> views = new ArrayList<>();
        allTasks.all().forEach(t -> views.add(TaskView.createFrom(t)));
        return views;
    }
}
