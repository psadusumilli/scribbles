package com.vijayrc.java8;

import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class JsTest {
   @Test
    public void shouldRunJavaScript() throws ScriptException, FileNotFoundException {
       ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
       engine.eval("function p(s) { print(s) }");
       engine.eval("p('Hello Nashorn');");
       engine.eval(new FileReader("/home/vijayrc/projs/VRC5/scribbles/java8/js/sample.js"));
       engine.eval(new FileReader("/home/vijayrc/projs/VRC5/scribbles/java8/js/sample2.js"));
   }
}
