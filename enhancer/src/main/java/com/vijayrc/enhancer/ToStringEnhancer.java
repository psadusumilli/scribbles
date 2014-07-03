package com.vijayrc.enhancer;

import com.vijayrc.meta.ToString;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ToStringEnhancer implements Enhancer {
    private static Logger log = LogManager.getLogger(ToStringEnhancer.class);

    @Override
    public void run(String packageName) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        for (Class<?> oldClass : new Reflections(packageName).getTypesAnnotatedWith(ToString.class)) {
            String className = oldClass.getName();
            CtClass newClass = pool.get(className);
            if (newClass.isFrozen()) newClass.defrost();

            CtMethod oldMethod = null;
            try {
                oldMethod = newClass.getDeclaredMethod("toString");
            } catch (NotFoundException e) {
                //do nothing
            }
            CtMethod newMethod = oldMethod != null? oldMethod:
                    new CtMethod(pool.get("java.lang.String"),"toString",new CtClass[]{},newClass);

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
            newMethod.setBody(body.toString());
            if(oldMethod == null)
                newClass.addMethod(newMethod);
            log.info("|+|" + className);
            newClass.writeFile(oldClass.getResource("/").getFile());
        }
    }
}
