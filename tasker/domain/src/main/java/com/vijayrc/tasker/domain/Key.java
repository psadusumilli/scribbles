package com.vijayrc.tasker.domain;

import com.vijayrc.meta.ToString;

@ToString
public class Key {
    private String title;
    private String uri;

    public static Key make(String title, String uri) {
        Key key = new Key();
        key.title = title;
        key.uri = uri;
        return key;
    }
    public String title() {
        return title;
    }
    public String uri() {
        return uri;
    }
}
