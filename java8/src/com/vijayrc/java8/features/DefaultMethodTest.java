package com.vijayrc.java8.features;

import org.junit.Test;

import static com.vijayrc.java8.Log.print;

public class DefaultMethodTest {
    interface MyInterface1 {
        void method1();
        default void method2() { print("interface1-method2");}
    }
    interface MyInterface2{
        default void method3() { print("interface2-method3");}//making this method2 is stopped by compiler
    }

    class MyClass1 implements MyInterface1 {
        @Override
        public void method1() {print("class1-method1");}
    }
    class MyClass2 implements MyInterface1 {
        @Override
        public void method1() {print("class2-method1");}
        @Override
        public void method2() {print("class2-method2");}
    }
    class MyClass3 implements MyInterface1,MyInterface2 {
        @Override
        public void method1() {print("class3-method1");}
    }

    @Test
    public void shouldRunDefaults(){
        MyClass1 myClass1 = new MyClass1();
        myClass1.method1();
        myClass1.method2();

        MyClass2 myClass2 = new MyClass2();
        myClass2.method1();
        myClass2.method2();

        MyClass3 myClass3 = new MyClass3();
        myClass3.method1();
        myClass3.method2();
        myClass3.method3();
    }

}
