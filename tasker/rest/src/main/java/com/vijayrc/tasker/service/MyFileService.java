package com.vijayrc.tasker.service;

import com.vijayrc.tasker.domain.MyFile;
import com.vijayrc.tasker.error.FileNotFound;
import com.vijayrc.tasker.repository.AllMyFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyFileService {
    @Autowired
    private AllMyFiles allMyFiles;

    public MyFile fetch(String id) throws FileNotFound {
        return allMyFiles.fetch(id);
    }
}
