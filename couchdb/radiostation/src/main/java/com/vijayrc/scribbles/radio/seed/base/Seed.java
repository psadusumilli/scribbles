package com.vijayrc.scribbles.radio.seed.base;


import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Seed {
    int order();
    String description();
    String key();
}
