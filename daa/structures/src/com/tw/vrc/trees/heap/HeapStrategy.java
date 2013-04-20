package com.tw.vrc.trees.heap;

public interface HeapStrategy<T extends Comparable> {
    void balance(HeapNode node);
}
