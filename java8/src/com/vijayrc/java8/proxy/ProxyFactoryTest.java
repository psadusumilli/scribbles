package com.vijayrc.java8.proxy;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.util.HashSet;
import java.util.Set;

import static com.vijayrc.java8.Log.print;
import static java.lang.reflect.Proxy.newProxyInstance;

public class ProxyFactoryTest {
    public static<T> T make(Class<T> classz, final T obj) {
        InvocationHandler handler = (proxy, method, args) -> {
            StringBuilder argsStr = new StringBuilder();
            for (Object arg : args) argsStr.append(arg).append("|");
            print(method.getName() + "=>" + argsStr.toString());
            return method.invoke(obj, args);
        };
        return (T) newProxyInstance(obj.getClass().getClassLoader(), new Class[]{classz}, handler);
    }
    @Test
    public void shouldWork(){
        Set<String> mySet = new HashSet<>();
        Set<String> proxySet = ProxyFactoryTest.make(Set.class,mySet);
        proxySet.add("cartman");
        proxySet.add("kyle");
        proxySet.remove("kyle");
    }
}
