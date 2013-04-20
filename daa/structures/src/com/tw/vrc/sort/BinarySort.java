package com.tw.vrc.sort;

import com.tw.vrc.trees.binary.BinaryTree;

import java.util.List;

/**
 *
 * O(n·lg(n)) time
 */
public class BinarySort<T extends Comparable> {
    private BinaryTree<T> tree;

    public void on(List<T> list){
        tree = new BinaryTree<T>();
        for (T t : list) {
           tree.insert(t);
        }
    }

    public String inOrder() {
        return tree.inOrder();
    }

    public void print() {
        tree.print();
    }
}
