package com.vijayrc.tasker.domain;

import com.vijayrc.meta.NoArgsConstr;
import com.vijayrc.meta.ToString;

import java.io.File;

@ToString
@NoArgsConstr
public class MyFile {
    private String id;
    private String card;
    private String path;
    private File file;

    public MyFile(String id, String card, String path) {
        this.id = id;
        this.card = card;
        this.path = path;
        this.file = new File(path);
    }
    public File file() {
        return file;
    }
    public String name() {
        return file.getName();
    }
    public String card() {
        return card;
    }
}
