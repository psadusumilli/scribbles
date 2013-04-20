package com.tw.vrc.trees.avl;

import com.tw.vrc.trees.nodes.Node;

/**
 * Version 1 - Balances only across top 3 nodes (root, A, B).
 * This is good for random data, but unsuited for nearly sorted data.
 *
 *                         R
 *                      A    B
 *                    A1 A2 B1 B2
 *
 *         Rotate left
 *                          B
 *                       R     B2
 *                     A   B1
 *                  A1   A2
 *
 *         Rotate right
 *                        A
 *                    A1     R
 *                        A2    B
 *                            B1  B2
 *
 *
 */
public class AVLTree1<T extends Comparable> {

    private Node<T> root = new Node<T>();

    public void insert(T t) {
        root.insert(t);
        balance();
    }

    private void balance() {
        int r = 0, l = 0, d;
        if (root.right != null) r = root.right.height();
        if (root.left != null) l = root.left.height();
        d = r - l;
        if (Math.abs(d) <= 1) return;
        if (d > 0) rotateLeft();
        if (d < 0) rotateRight();
    }

    private void rotateLeft() {
        Node<T> A = root.left;
        Node<T> B = root.right;
        Node<T> R = new Node<T>(root.value());
        R.left = A;
        R.right = B.left;

        root = B;
        root.left = R;
    }

    private void rotateRight() {
       Node<T> A = root.left;
       Node<T> B = root.right;
       Node<T> R = new Node<T>(root.value());
       R.left = A.right;
       R.right = B;

       root = A;
       root.right = R;
    }

    public void print() {
        root.print();
    }
}
