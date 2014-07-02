package com.vijayrc.enhancer;

import com.vijayrc.meta.NoArgsConstr;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

public class NoArgsConstrEnhancer implements Enhancer {
    private static Logger log = LogManager.getLogger(NoArgsConstrEnhancer.class);

    @Override
    public void run(String packageName) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        Reflections reflections = new Reflections(packageName);

        for (Class aClass : reflections.getTypesAnnotatedWith(NoArgsConstr.class)) {
            CtClass ctClass = pool.get(aClass.getName());
            boolean hasNoArgsConstructor = false;
            for (CtConstructor constructor : ctClass.getConstructors())
                if(constructor.getParameterTypes().length == 0) {
                    hasNoArgsConstructor = true;
                    log.info("no args constructor already present");
                    break;
                }
            if(!hasNoArgsConstructor){
                ctClass.addConstructor(new CtConstructor(new CtClass[]{},ctClass));
                log.info("added for: "+aClass.getName());
            }
        }
    }
}
