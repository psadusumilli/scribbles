package com.tw.vrc.dictionaries;

import java.util.ArrayList;

public class Bucket<K, T extends Object> {
    private Integer hash;
    private ArrayList<Mug> mugs = new ArrayList<Mug>();

    public Bucket(Integer hash) {
        this.hash = hash;
    }

    public void add(K key, T value) {
        Mug mug = new Mug(key, value);
        mugs.remove(mug);
        mugs.add(mug);
    }

    public void remove(K key, T value) {
        mugs.remove(new Mug(key, value));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bucket bucket = (Bucket) o;
        if (hash != null ? !hash.equals(bucket.hash) : bucket.hash != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return hash != null ? hash.hashCode() : 0;
    }

    public T find(K key) {
        Mug toFind = new Mug(key);
        for (Mug mug : mugs) {
            if(mug.equals(toFind)) return (T) mug.value();
        }
        return null;
    }
}
