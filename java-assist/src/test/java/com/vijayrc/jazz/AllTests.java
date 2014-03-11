package com.vijayrc.jazz;

import javassist.*;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.vijayrc.jazz.Log.print;

public class AllTests {
    @Test
    public void shouldAddInheritance() throws IOException, CannotCompileException, NotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        ClassPool pool = ClassPool.getDefault();
        CtClass circleClass = pool.get("com.vijayrc.jazz.Circle");
        circleClass.setSuperclass(pool.get("com.vijayrc.jazz.Shape"));

        CtMethod areaMethod = circleClass.getDeclaredMethod("area");
        areaMethod.insertAfter("System.out.println(\"calculated area..\");");
        circleClass.writeFile();

        Circle c = (Circle) circleClass.toClass().newInstance();
        c.setRadius(10);
        print("area of circle=" + c.area());

        Method getName = c.getClass().getMethod("getName");
        print(getName.invoke(c));
    }
    @Test
    public void shouldCreateANewClass() throws NotFoundException, CannotCompileException {
        ClassPool pool = ClassPool.getDefault();
        CtClass squareClass = pool.makeClass("com.vijayrc.Square");
        CtClass doubleClass = pool.get("java.lang.Double");

        CtConstructor constr = new CtConstructor(new CtClass[]{doubleClass},squareClass);
        squareClass.addConstructor(constr);




    }

}
