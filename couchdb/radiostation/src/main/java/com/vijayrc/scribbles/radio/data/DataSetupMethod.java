package com.vijayrc.scribbles.radio.data;

import java.lang.reflect.Method;

public class DataSetupMethod {
    private Object bean;
    private Method method;
    private String description;
    private Integer order;

    public DataSetupMethod(Object bean, Method method, String description, Integer order) {
        this.bean = bean;
        this.method = method;
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
}
