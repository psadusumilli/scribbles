package com.vijayrc.bean;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.Set;

import static org.apache.commons.lang.StringUtils.capitalize;

public class Enhance {
    public static void main(String[] args) throws Exception {
        print("start:.....");
        ClassPool pool = ClassPool.getDefault();
        Reflections reflections = new Reflections("com.vijayrc");
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(View.class);
        for (Class<?> aClass : classes) {
            CtClass ctClass = pool.get(aClass.getName());
            for (Field field : aClass.getDeclaredFields()) {
                String fieldName = field.getName();
                CtClass fieldType = pool.get(field.getGenericType().getTypeName());

                CtMethod getter  = new CtMethod(
                        fieldType,
                        "get"+ capitalize(fieldName),
                        null,
                        ctClass);
                getter.setBody("return " + fieldName + ";");
                ctClass.addMethod(getter);

                CtMethod setter  = new CtMethod(
                        CtClass.voidType,
                        "set"+ capitalize(fieldName),
                        new CtClass[]{fieldType},
                        ctClass);
                setter.setBody("this." + fieldName + " = $1;");
                ctClass.addMethod(setter);

                print("done getter/setter:"+fieldName);
            }
            ctClass.writeFile(aClass.getResource("/").getFile());
        }
        print("end:.....");
    }
    private static void print(String msg){
        System.out.println(msg);
    }
}
