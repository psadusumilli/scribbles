package com.vijayrc.tasker.view;

import com.vijayrc.meta.Bean;
import com.vijayrc.tasker.domain.Task;

import javax.ws.rs.BeanParam;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Bean
public class TaskView {
    private String title;
    private String summary;
    private String startBy;
    private String endBy;

    public static TaskView createFrom(Task task){
        TaskView taskView = new TaskView();
        if(task != null){
            taskView.title = task.title();
            taskView.summary = task.summary();
            taskView.startBy = task.startBy();
            taskView.endBy = task.endBy();
        }
        return taskView;
    }
}
