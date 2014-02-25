package com.vijayrc.java8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.vijayrc.java8.Log.print;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**A lambda expression is like syntactic sugar for an anonymous class1 with one method whose type is inferred,
 * like Comparator.compare, Runnable.run*/
public class AllFunctions {

    private class Value{
        private String value;
        public Value(String value) {
            this.value = value;
        }
        public void append(String suffix){
            this.value = value+suffix;
        }
        @Override
        public String toString() {
            return value;
        }
    }

    /**takes string argument and returns integer*/
    Function<String, Integer> f1 = s -> {return s.length();};
    Function<String, Integer> f2 = s -> s.length();
    Function<String, Integer> f3 = String::length;

    Comparator<Integer> c1 = (x, y) -> (x < y) ? -1 : ((x.equals(y)) ? 0 : 1);
    Comparator<Integer> c2 = (Integer x, Integer y) -> (x < y) ? -1 : ((x > y) ? 1 : 0);
    Comparator<String> c3 = (String x, String y) -> (x.compareTo(y) < 0) ? -1 : ((x.equals(y)) ? 0 : 1);

    Supplier<Integer> c42 = () -> 42;

    Runnable r1 = () -> print("r1");
    Runnable r2 = () -> print("r2");

    Consumer<Value> con1 = z -> z.append("c1");
    Consumer<Value> con2 = z -> z.append("c2|");
    Consumer<Value> con3 = System.out::print;


    @Test
    public void shouldTestFunctions(){
        assertThat(f1.apply("vijay"), is(5));
        assertThat(f2.apply("vijay"), is(5));
        assertThat(f3.apply("vijay"), is(5));

        assertThat(c1.compare(1,2), is(-1));
        assertThat(c2.compare(1,2), is(-1));
        assertThat(c3.compare("abc", "vcf"), is(-1));
        assertThat(c42.get(), is(42));

        r1.run();
        r2.run();

        List<Value> list = new ArrayList<>();
        for(int i=1;i<6;i++) list.add(new Value(i+""));
        list.forEach(con1.andThen(con2));
        list.forEach(con3);
    }
}
