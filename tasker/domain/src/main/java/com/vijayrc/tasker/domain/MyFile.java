package com.vijayrc.tasker.domain;

import com.vijayrc.meta.ToString;

import java.io.File;

@ToString
public class MyFile {
    private String id;
    private String card;
    private String path;
    private File file;

    public MyFile() {
    }

    public MyFile(String id, String card, String path) {
        this.id = id;
        this.card = card;
        this.path = path;
        this.file = new File(path);
    }
    public MyFile(File file, String card) {
        this.card = card;
        this.file = file;
    }
    public String id() {
        return id;
    }
    public String card() {
        return card;
    }
    public File file() {
        return file;
    }
    public String name() {
        return file.getName();
    }

}
