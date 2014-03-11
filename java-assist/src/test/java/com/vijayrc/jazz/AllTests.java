package com.vijayrc.jazz;

import javassist.*;
import org.junit.Test;

import java.io.IOException;

import static com.vijayrc.jazz.Log.print;

public class AllTests {
    @Test
    public void shouldAddInheritance() throws IOException, CannotCompileException, NotFoundException, IllegalAccessException, InstantiationException {
        ClassPool pool = ClassPool.getDefault();
        CtClass ct = pool.get("com.vijayrc.jazz.Circle");
        ct.setSuperclass(pool.get("com.vijayrc.jazz.Shape"));
        CtMethod m = ct.getDeclaredMethod("area");
        m.insertAfter("System.out.println(\"calculating area..\");");
        ct.writeFile();

        Circle c = (Circle) ct.toClass().newInstance();
        c.setRadius(10);
        print("area of circle=" + c.area());

    }
}
