package com.vijayrc.scribbles.radio.seed.base;

import java.lang.reflect.Method;

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

    public String description() {
        return description;
    }

    public Integer order() {
        return order;
    }

    public String key() {
        return key;
    }

    private String uniqueName(){
        return order+"-"+key;
    }

    @Override
    public int compareTo(Object other) {
        return this.uniqueName().compareTo(((SeedMethod) other).uniqueName());
    }
}
