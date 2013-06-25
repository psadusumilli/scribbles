package com.vijayrc.scribbles;

/**
 * Created by vchakrav on 6/25/13.
 */
public abstract class BaseMusic implements Music {

    private final String name;

    protected BaseMusic(String name) {
        this.name = name;
    }

    @Override
    public boolean is(String name) {
        return this.name.equalsIgnoreCase(name);
    }
}
