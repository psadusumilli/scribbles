package com.tw.vrc.trees.binary;


import com.tw.vrc.trees.nodes.Node;

import java.util.List;

public class BinaryTree<T extends Comparable> {
    private Node<T> root = new Node<T>();

    public Node<T> on(List<T> list) {
        recurse(list);
        return root;
    }

    public void recurse(List<T> list) {
        if (list.size() <= 2) {
            for (T t : list) root.insert(t);
            return;
        }
        int middleIndex = list.size() / 2;
        root.insert(list.get(middleIndex));
        recurse(list.subList(0, middleIndex));
        recurse(list.subList(middleIndex + 1, list.size()));
    }

    public T max() {
        Node<T> node = root;
        while (node.right != null) node = node.right;
        return node.value();
    }

    public T min() {
        Node<T> node = root;
        while (node.left != null) node = node.left;
        return node.value();
    }

    public boolean exists(T t) {
        return root.exists(t);
    }

    public String inOrder() {
        return root.inOrder();
    }

    public String preOrder() {
        return root.preOrder();
    }

    public String postOrder() {
        return root.postOrder();
    }

    public void insert(T t) {
        root.insert(t);
    }

    public void print() {
        root.print();
    }

    public Integer height() {
        return root.height();
    }

    public Integer occurrences(T t) {
        return root.occurrences(t);
    }
}
