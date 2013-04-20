package com.tw.vrc.dictionaries;

public class Mug<K, T> {
    private K key;
    private T value;

    public Mug(K key, T value) {
        this.key = key;
        this.value = value;
    }

    public Mug(K key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mug item = (Mug) o;
        if (key != null ? !key.equals(item.key) : item.key != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return key != null ? key.hashCode() : 0;
    }

    public T value() {
        return value;
    }
}
