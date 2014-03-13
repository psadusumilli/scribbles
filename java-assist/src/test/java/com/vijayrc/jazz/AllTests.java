package com.vijayrc.jazz;

import javassist.*;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import static com.vijayrc.jazz.Log.print;
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

}
