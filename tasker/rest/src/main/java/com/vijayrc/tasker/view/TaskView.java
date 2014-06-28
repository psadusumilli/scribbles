package com.vijayrc.tasker.view;

import com.vijayrc.tasker.domain.Task;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TaskView {
    private String title;
    private String summary;
    private String startBy;
    private String endBy;

    public static TaskView createFrom(Task task){
        TaskView taskView = new TaskView();
        taskView.title = task.title();
        taskView.summary = task.summary();
        taskView.startBy = task.startBy();
        taskView.endBy = task.endBy();
        return taskView;
    }

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public String getSummary() { return summary;}
    public void setSummary(String summary) { this.summary = summary;}
    public String getStartBy() { return startBy;}
    public void setStartBy(String startBy) {this.startBy = startBy;}
    public String getEndBy() {return endBy;}
    public void setEndBy(String endBy) {this.endBy = endBy;}
}
