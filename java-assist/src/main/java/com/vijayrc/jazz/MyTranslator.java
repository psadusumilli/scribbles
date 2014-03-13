package com.vijayrc.jazz;

import javassist.*;

public class MyTranslator implements Translator {
    public void start(ClassPool pool) throws NotFoundException, CannotCompileException {
    }

    public void onLoad(ClassPool pool, String classname) throws NotFoundException, CannotCompileException {
        CtClass cc = pool.get(classname);
        cc.setModifiers(Modifier.PUBLIC);
    }
}
