package com.vijayrc.scribbles.radio.seed.base;

import lombok.Getter;

import java.lang.reflect.Method;

@Getter
public class SeedMethod implements Comparable {
    private Object bean;
    private Method method;
    private String description;
    private String key;
    private Integer order;

    public SeedMethod(Object bean, Method method, String description, String key, Integer order) {
        this.bean = bean;
        this.method = method;
        this.key = key;
        this.order = order;
        this.description = description;
    }

    public void run() throws Exception {
        method.invoke(bean, null);
    }

    private String uniqueName(){
        return order+"-"+key;
    }

    @Override
    public int compareTo(Object other) {
        return this.uniqueName().compareTo(((SeedMethod) other).uniqueName());
    }
}
