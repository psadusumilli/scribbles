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
        Reflections reflections = new Reflections(packageName);
        for (Class<?> aClass : reflections.getTypesAnnotatedWith(ToString.class)) {
            if(aClass.getMethod("toString") != null)
                return;
            CtClass ctClass = pool.get(aClass.getName());
            CtMethod toString  = new CtMethod(pool.get("java.lang.String"),"toString",null,ctClass);
            StringBuffer body = new StringBuffer("return \""+aClass.getSimpleName()+"[\"+");
            for (Field field : aClass.getDeclaredFields()) {
                String fieldName = field.getName();
                body.append("\""+fieldName+"\"").append("+\"=\"+this.").append(fieldName).append("+\"|\"+");
            }
            body.append("\"]\";");
            toString.setBody(body.toString());
            ctClass.addMethod(toString);
            ctClass.writeFile(aClass.getResource("/").getFile());
            log.info("done:" + aClass.getName());
        }
    }
}
