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
        for (Class oldClass : new Reflections(packageName).getTypesAnnotatedWith(NoArgsConstr.class)) {
            String className = oldClass.getName();
            CtClass newClass = pool.get(className);

            boolean hasNoArgsConstructor = false;
            for (CtConstructor constructor : newClass.getConstructors())
                if(constructor.getParameterTypes().length == 0) {
                    hasNoArgsConstructor = true;
                    log.info("|-|" + className);
                    break;
                }
            if(!hasNoArgsConstructor){
                newClass.addConstructor(new CtConstructor(new CtClass[]{}, newClass));
                log.info("|+|" + className);
            }
        }
    }
}
