package com.vijayrc.tasker.view;

import com.vijayrc.tasker.domain.Task;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Data
public class TaskView {
    private String id;
    private String title;
    private String summary;
    private String startBy;
    private String endBy;

    public static TaskView createFrom(Task task){
        TaskView taskView = new TaskView();
        if(task != null){
            taskView.id = task.id();
            taskView.title = task.title();
            taskView.summary = task.summary();
            taskView.startBy = task.startBy();
            taskView.endBy = task.endBy();
        }
        return taskView;
    }

}
