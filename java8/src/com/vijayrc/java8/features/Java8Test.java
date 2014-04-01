package com.vijayrc.java8.features;

import com.vijayrc.java8.Log;
import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.vijayrc.java8.Log.print;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**A lambda expression is like syntactic sugar for an anonymous class1 with one method whose type is inferred,
 * like Comparator.compare, Runnable.run*/
public class Java8Test {

    private class Item {
        private String value;
        public Item(String value) {this.value = value;}
        public void append(String suffix){ this.value = value+suffix;}
        @Override
        public String toString() {return value;}
    }
    public class NullChecker {
        public int count = 0;
        public void isNull(String s) {
            if (s != null) return;
            count++;
        }
    }

    Runnable r1 = () -> print("r1");
    Runnable r2 = () -> print("r2");

    Function<String, Integer> f1 = s -> {return s.length();};
    Function<String, Integer> f2 = s -> s.length();
    Function<String, Integer> f3 = String::length;

    Comparator<Integer> c1 = (x, y) -> (x < y) ? -1 : ((x.equals(y)) ? 0 : 1);
    Comparator<Integer> c2 = (Integer x, Integer y) -> (x < y) ? -1 : ((x > y) ? 1 : 0);
    Comparator<String> c3 = (String x, String y) -> (x.compareTo(y) < 0) ? -1 : ((x.equals(y)) ? 0 : 1);

    Supplier<Integer> s42 = () -> 42;

    Consumer<Item> con1 = z -> z.append("c1");
    Consumer<Item> con2 = z -> z.append("c2|");
    Consumer<Item> con3 = System.out::print;

    @Test
    public void shouldTestFunctionInterfaces(){
        assertThat(f1.apply("kenny"), is(5));
        assertThat(f2.apply("kenny"), is(5));
        assertThat(f3.apply("kenny"), is(5));

        assertThat(c1.compare(1,2), is(-1));
        assertThat(c2.compare(1,2), is(-1));
        assertThat(c3.compare("abc", "vcf"), is(-1));

        assertThat(s42.get(), is(42));

        r1.run();
        r2.run();

        List<Item> items = new ArrayList<>();
        for(int i=1;i<6;i++) items.add(new Item(i+""));
        items.forEach(con1.andThen(con2));
        items.forEach(con3);
    }
    @Test
    public void shouldTestMethodReferences(){
        NullChecker checker = new NullChecker();
        List<String> boys = Arrays.asList("kyle", null, "cartman", "kenny", "stan");
        boys.forEach(checker::isNull);
        assertThat(checker.count, is(1));
    }
    @Test
    public void shouldTestCollect(){
        List<Item> item = new ArrayList<>();
        for(int i=1;i<6;i++) item.add(new Item(i+""));
        List<String> collect = item.stream().map(Item::toString).collect(Collectors.toList());
        collect.forEach(Log::print);
    }
    @Test
    public void shouldRunJavaScript() throws ScriptException, FileNotFoundException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval("function p(s) { print(s) }");
        engine.eval("p('Hello Nashorn');");
        engine.eval(new FileReader("/home/vijayrc/projs/VRC5/scribbles/java8/js/sample.js"));
    }
}
