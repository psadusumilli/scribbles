package com.vijayrc.enhancer;

import com.vijayrc.meta.ToString;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import java.lang.reflect.Field;

public class ToStringEnhancer implements Enhancer {
    private static Logger log = LogManager.getLogger(ToStringEnhancer.class);

    @Override
    public void run(String packageName) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        for (Class<?> oldClass : new Reflections(packageName).getTypesAnnotatedWith(ToString.class)) {
            String className = oldClass.getName();
            if(oldClass.getMethod("toString") != null){
                log.info("|-|" + className);
                return;
            }
            CtClass newClass = pool.get(className);
            CtMethod method  = new CtMethod(pool.get("java.lang.String"),"toString",null,newClass);

            StringBuilder body = new StringBuilder("return \""+oldClass.getSimpleName()+"[\"+");
            for (Field field : oldClass.getDeclaredFields()) {
                String fieldName = field.getName();
                body.append("\"")
                    .append(fieldName)
                    .append("\"")
                    .append("+\"=\"+this.")
                    .append(fieldName)
                    .append("+\"|\"+");
            }
            body.append("\"]\";");

            method.setBody(body.toString());
            newClass.addMethod(method);
            newClass.writeFile(oldClass.getResource("/").getFile());
            log.info("|+|" + className);
        }
    }
}
