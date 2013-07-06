package com.vijayrc.scribbles.radio.data;


import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSetup {
    int order();
    String description();
    String key();
}
