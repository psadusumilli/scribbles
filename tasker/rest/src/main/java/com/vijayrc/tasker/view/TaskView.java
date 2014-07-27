package com.vijayrc.tasker.view;

import com.vijayrc.meta.Bean;
import com.vijayrc.meta.ToString;
import com.vijayrc.tasker.domain.Task;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlRootElement;

@Bean
@ToString
@XmlRootElement
@ApiModel("representation of task")
public class TaskView {
    @ApiModelProperty("parent card number")
    private String card;
    @ApiModelProperty("current status of task, can be any of [complete, progress, invalid, park]")
    private String status;
    @ApiModelProperty("brief explanation of task")
    private String description;

    public static TaskView createFrom(Task task){
        TaskView taskView =  new TaskView();
        taskView.card = task.card();
        taskView.description = task.description();
        taskView.status = task.status();
        return taskView;
    }
}
