package com.vijayrc.tasker.domain;

import com.vijayrc.meta.ToString;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.InputStream;

@ToString
public class MyFile {
    private String id;
    private String card;
    private String path;
    private String name;
    private File file;
    private InputStream inputStream;

    public MyFile() {
    }
    public static MyFile readInstance(String id, String card, String path) {
        MyFile myFile = new MyFile();
        myFile.id = id;
        myFile.card = card;
        myFile.path = path;
        myFile.file = new File(path);
        return myFile;
    }
    public static MyFile writeInstance(InputStream inputStream, String card, String name) {
        MyFile myFile = new MyFile();
        myFile.inputStream = inputStream;
        myFile.card = card;
        myFile.name = name;
        return myFile;
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
        return file != null? file.getName():name;
    }
    public InputStream inputStream() {
        return inputStream;
    }
    public String mediaType(){
        return new MimetypesFileTypeMap().getContentType(file);
    }
}
