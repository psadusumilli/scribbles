package com.vijayrc.tasker.service;

import com.vijayrc.tasker.domain.MyFile;
import com.vijayrc.tasker.error.FileNotFound;
import com.vijayrc.tasker.repository.AllMyFiles;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class MyFileService {
    @Autowired
    private AllMyFiles allMyFiles;

    public MyFile fetch(String id) throws FileNotFound {
        return allMyFiles.fetch(id);
    }
    public Integer create(MyFile myFile) throws Exception {
        return allMyFiles.create(myFile);
    }
    public List<String> read(String id) throws Exception {
        MyFile myFile = allMyFiles.fetch(id);
        return FileUtils.readLines(myFile.file());
    }
}
