package com.tw.vrc.sort;

import java.util.List;

public abstract class AbstractSort<T extends Comparable> implements Sort<T>  {
    protected void swap(List<T> input, int a, int b) {
        T t = input.get(a);
        input.set(a, input.get(b));
        input.set(b, t);
    }
}
