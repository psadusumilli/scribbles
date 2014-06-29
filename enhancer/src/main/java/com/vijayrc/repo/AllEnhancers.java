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
            enhancers.add(aClass.newInstance());
    }
    public void run(String packageName) throws Exception{
        for (Enhancer enhancer : enhancers){
            log.info(enhancer.name()+"--------------------#start");
            enhancer.run(packageName);
            log.info(enhancer.name()+"--------------------#end");
        }
    }
    public static void main(String[] args) throws Exception {
        new AllEnhancers().run("com.vijayrc");
    }
}

