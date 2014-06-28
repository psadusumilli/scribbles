package com.vijayrc.jazz;

import com.vijayrc.bean.View;

import java.util.Date;

@View
public class Task {
    public Long id;
    private String title;
    private String summary;
    private Date startBy;
    private Date endBy;
}
