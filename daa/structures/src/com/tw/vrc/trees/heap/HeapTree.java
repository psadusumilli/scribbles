package com.tw.vrc.trees.heap;

public class HeapTree<T extends Comparable> {
    private HeapStrategy strategy;
    private HeapNode<T> root;

    public HeapTree(HeapStrategy strategy) {
        this.strategy = strategy;
        this.root = new HeapNode<T>(strategy);
    }

    public void add(T t) {
        checkRoot();
        root.insert(t);
    }

    public void print() {
        checkRoot();
        root.print();
    }

    private void checkRoot() {
        if (root == null) root = new HeapNode<T>(strategy);
    }

    public T remove() {
        if (root == null) return null;
        T t = root.value;
        T v = root.lastLeafValue();
        if (t.equals(v)) {
            root = null;
        } else {
            root.value = v;
            root.balance();
        }
        return t;
    }
}
