package com.vijayrc.tasker.repository;

import com.vijayrc.tasker.domain.Key;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.vijayrc.tasker.domain.Key.make;

@Repository
public class AllKeys {
    private static Logger log = LogManager.getLogger(AllKeys.class);

    public List<Key> fetch(String key){
        List<Key> keys = new ArrayList<>();
        keys.add(make("card-4", "/cards/4"));
        keys.add(make("card-2", "/cards/2"));
        keys.add(make("card-4-task-1", "/cards/4/task2/1"));
        keys.add(make("card-1-task-3", "/cards/1/tasks/3"));
        keys.add(make("file-2", "/files/2"));

        StringBuilder msg = new StringBuilder();
        keys.forEach(msg::append);
        log.info("return|key=" + key + "|keys=" + msg);
        return keys;
    }
}
