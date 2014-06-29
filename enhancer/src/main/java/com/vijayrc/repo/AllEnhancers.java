package com.vijayrc.repo;

import com.vijayrc.enhancer.Enhancer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;

public class AllEnhancers {
    private static Logger log = LogManager.getLogger(AllEnhancers.class);
    private List<Enhancer> enhancers = new ArrayList<>();

    public AllEnhancers() throws Exception {
        Reflections reflections = new Reflections("com.vijayrc.enhancer");
        for (Class<? extends Enhancer> aClass : reflections.getSubTypesOf(Enhancer.class))
            this.enhancers.add(aClass.newInstance());
    }

    public static void main(String[] args) throws Exception {
        log.info("start:...");
        AllEnhancers allEnhancers = new AllEnhancers();
        log.info(allEnhancers.enhancers.size());
        log.info("end:...");
    }


}

