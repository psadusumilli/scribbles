package com.vijayrc.tasker.view;

import com.vijayrc.meta.Bean;
import com.vijayrc.meta.ToString;
import com.vijayrc.tasker.domain.Task;

import javax.xml.bind.annotation.XmlRootElement;

@Bean
@ToString
@XmlRootElement
public class TaskView {
    private String card;
    private String status;
    private String description;

    public static TaskView createFrom(Task task){
        TaskView taskView =  new TaskView();
        taskView.card = task.card();
        taskView.description = task.description();
        taskView.status = task.status();
        return taskView;
    }
}
