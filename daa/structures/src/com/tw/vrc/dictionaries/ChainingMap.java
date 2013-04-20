package com.tw.vrc.dictionaries;

import java.util.ArrayList;
import java.util.List;

public class ChainingMap<K, T> {
    private List<Bucket> buckets = new ArrayList<Bucket>();

    public void put(K key, T value) {
        Bucket bucket = new Bucket(key.hashCode());
        if (buckets.contains(bucket)) {
            buckets.get(buckets.indexOf(bucket)).add(key, value);
        } else {
            bucket.add(key, value);
            buckets.add(bucket);
        }
    }

    public T find(K key) {
        Bucket bucket = new Bucket(key.hashCode());
        if (!buckets.contains(bucket)) return null;
        bucket = buckets.get(buckets.indexOf(bucket));
        return (T) bucket.find(key);
    }
}
