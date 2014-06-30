package com.vijayrc.enhancer;

import com.vijayrc.meta.Bean;
import javassist.ClassPool;
import javassist.CtClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

public class ToStringEnhancer implements Enhancer {
    private static Logger log = LogManager.getLogger(ToStringEnhancer.class);

    @Override
    public void run(String packageName) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        Reflections reflections = new Reflections(packageName);
        for (Class<?> aClass : reflections.getTypesAnnotatedWith(Bean.class)) {
            CtClass ctClass = pool.get(aClass.getName());



            //~update class file
            ctClass.writeFile(aClass.getResource("/").getFile());
        }
    }
    @Override
    public String name() {
        return "ToStringEnhancer";
    }
}
