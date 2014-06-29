package com.vijayrc.jazz;

import com.vijayrc.bean.View;
import javassist.*;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.reflections.Reflections;
import sun.reflect.Reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

import static com.vijayrc.jazz.Log.print;
import static org.apache.commons.lang.StringUtils.capitalize;
import static org.junit.Assert.assertEquals;

public class AllTests {

    @Test
    public void shouldAddInheritanceAndInjectCodeIntoMethod() throws Exception{
        ClassPool pool = ClassPool.getDefault();
        CtClass circleClass = pool.get("com.vijayrc.jazz.Circle");
        circleClass.setSuperclass(pool.get("com.vijayrc.jazz.Shape"));

        CtMethod areaMethod = circleClass.getDeclaredMethod("area");
        areaMethod.insertBefore("System.out.println(\"calculating area..\");");
        circleClass.writeFile();

        Circle circle = (Circle) circleClass.toClass().newInstance();
        circle.setRadius(10);
        print("area of circle=" + circle.area());

        Method getName = circle.getClass().getMethod("getName");
        print(getName.invoke(circle));
    }
    @Test
    public void shouldCreateANewClassOutOfThinAir() throws Exception{
        ClassPool pool = ClassPool.getDefault();
        CtClass squareClass = pool.makeClass("com.vijayrc.Square");
        CtClass doubleClass = pool.get("java.lang.Double");

        //add field
        CtField sideField = new CtField(doubleClass,"side",squareClass);
        squareClass.addField(sideField);

        //add constructor
        CtConstructor ctConstructor = new CtConstructor(new CtClass[]{doubleClass},squareClass);
        ctConstructor.setBody("this.side=$1;");
        squareClass.addConstructor(ctConstructor);

        //add method
        CtMethod areaMethod  = new CtMethod(doubleClass,"area",null,squareClass);
        areaMethod.setBody("return new Double(Math.pow(this.side.doubleValue(),2d));");
        squareClass.addMethod(areaMethod);

        //invoke
        Constructor<?> constructor = squareClass.toClass().getConstructor(Double.class);
        Object square = constructor.newInstance(2d);
        Method area = square.getClass().getMethod("area");
        assertEquals(area.invoke(square), 4d);
    }

    @Test
    public void shouldModifySystemClass() throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("java.lang.String");
        CtField f = new CtField(CtClass.intType, "hiddenValue", cc);
        f.setModifiers(Modifier.PUBLIC);
        cc.addField(f);
        cc.writeFile(".");
        print(String.class.getField("hiddenValue").getName());
    }

    @Test
    public void shouldGenerateGettersSetters() throws Exception {
        ClassPool pool = ClassPool.getDefault();
        Reflections reflections = new Reflections("com.vijayrc");
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(View.class);
        for (Class<?> aClass : classes) {
            CtClass ctClass = pool.get(aClass.getName());
            for (Field field : aClass.getDeclaredFields()) {
                String fieldName = field.getName();
                CtClass fieldType = pool.get(field.getGenericType().getTypeName());

                CtMethod getter  = new CtMethod(fieldType,"get"+ capitalize(fieldName), null, ctClass);
                getter.setBody("return " + fieldName + ";");
                ctClass.addMethod(getter);

                CtMethod setter  = new CtMethod(CtClass.voidType, "set"+ capitalize(fieldName), new CtClass[]{fieldType},ctClass);
                setter.setBody("this." + fieldName + " = $1;");
                ctClass.addMethod(setter);
            }
            ctClass.writeFile(aClass.getResource("/").getFile());
        }
    }

    @Test
    public void shouldCheckOnFile() throws Exception {
        Task t = new Task();
        t.id = 1l;
        for (Method method : t.getClass().getDeclaredMethods()) {
            print(method);
            if(method.getName().equals("setTitle")){
                method.invoke(t,"ttt");
            }
            method.setAccessible(true);
        }
        Method getId = t.getClass().getDeclaredMethod("getId");
        Method getTitle= t.getClass().getDeclaredMethod("getTitle");
        print(getId.invoke(t));
        print(getTitle.invoke(t));
    }

}
