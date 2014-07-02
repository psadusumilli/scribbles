package com.vijayrc.enhancer;

import com.vijayrc.meta.Bean;
import javassist.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import java.lang.reflect.Field;

import static org.apache.commons.lang.StringUtils.capitalize;
import static org.apache.commons.lang.StringUtils.contains;

public class BeanEnhancer implements Enhancer{
    private static Logger log = LogManager.getLogger(BeanEnhancer.class);

    public void run(String packageName) throws Exception {
        ClassPool pool = ClassPool.getDefault();

        for (Class<?> oldClass : new Reflections(packageName).getTypesAnnotatedWith(Bean.class)) {
            String className = oldClass.getName();
            CtClass newClass = pool.get(className);

            for (Field field : oldClass.getDeclaredFields()) {
                String fieldName = field.getName();
                CtClass fieldClass = pool.get(field.getGenericType().getTypeName());
                getter(newClass, fieldName, fieldClass);
                setter(newClass, fieldName, fieldClass);
            }
            boolean hasNoArgsConstructor = false;
            for (CtConstructor constructor : newClass.getConstructors())
                if(constructor.getParameterTypes().length == 0) {
                    hasNoArgsConstructor = true;
                    break;
                }
            if(!hasNoArgsConstructor)
                newClass.addConstructor(new CtConstructor(new CtClass[]{},newClass));

            newClass.writeFile(oldClass.getResource("/").getFile());
            log.info("|+|" + className);
        }
    }
    private void setter(CtClass newClass, String fieldName, CtClass fieldType) throws CannotCompileException {
        CtMethod setter  = new CtMethod( CtClass.voidType, "set"+ capitalize(fieldName), new CtClass[]{fieldType}, newClass);
        setter.setBody("this." + fieldName + " = $1;");
        newClass.addMethod(setter);
    }
    private void getter(CtClass newClass, String fieldName, CtClass fieldType) throws CannotCompileException {
        CtMethod getter  = new CtMethod(fieldType,"get"+ capitalize(fieldName), null, newClass);
        getter.setBody("return " + fieldName + ";");
        newClass.addMethod(getter);
    }
}
