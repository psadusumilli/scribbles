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

    public List<TaskView> getFor(String card){
        List<TaskView> views = new ArrayList<>();
        allTasks.getForCard(card).forEach(t -> views.add(TaskView.createFrom(t)));
        return views;
    }
}
