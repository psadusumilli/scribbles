package com.tw.vrc.trees.avl;

import com.tw.vrc.trees.nodes.Node;

public class AVLTree2<T extends Comparable> {

    private Node<T> root = new Node<T>();

    public void insert(T t) {
        root.insert(t);
        root.balance();
    }

    public void print() {
        root.print();
    }
}
