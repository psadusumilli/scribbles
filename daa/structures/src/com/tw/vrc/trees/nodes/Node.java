package com.tw.vrc.trees.nodes;

import com.tw.vrc.utilities.Print;
import com.tw.vrc.utilities.Printable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node<T extends Comparable> implements Printable {
    private T value;
    public Node<T> right;
    public Node<T> left;
    public Node<T> parent;

    public Node() {
    }

    public Node(T value) {
        this.value = value;
    }

    public Node(T value, Node<T> parent) {
        this.value = value;
        this.parent = parent;
    }

    public T value() {
        return value;
    }

    public List<Node<T>> children() {
        return Arrays.asList(right, left);
    }

    public void insert(T t) {
        if (value == null) {
            value = t;
            return;
        }
        if (value.compareTo(t) > 0) {
            if (left == null) left = new Node<T>(t, this);
            else left.insert(t);
        } else {
            if (right == null) right = new Node<T>(t, this);
            else right.insert(t);
        }
    }

    public boolean exists(T t) {
        if (t.equals(value)) return true;
        if (t.compareTo(value) > 0 && right != null) return right.exists(t);
        else if (left != null) return left.exists(t);
        else return false;
    }

    public Integer occurrences(T t) {
        if (t.equals(value)) {
            Integer count = 1;
            Node node = right;
            while (node != null) {
                count++;
                node = node.right;
            }
            return count;
        }
        if (t.compareTo(value) > 0 && right != null) return right.occurrences(t);
        else if (left != null) return left.occurrences(t);
        else return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        if (value != null ? !value.equals(node.value) : node.value != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        return value != null ? value.toString() : "*";
    }

    public String preOrder() {
        StringBuilder b = new StringBuilder();
        b.append(value);
        if (left != null) b.append(left.preOrder());
        if (right != null) b.append(right.preOrder());
        return b.toString();
    }

    public String postOrder() {
        StringBuilder b = new StringBuilder();
        if (left != null) b.append(left.postOrder());
        if (right != null) b.append(right.postOrder());
        b.append(value);
        return b.toString();
    }

    public String inOrder() {
        StringBuilder b = new StringBuilder();
        if (left != null) b.append(left.inOrder());
        b.append(value);
        if (right != null) b.append(right.inOrder());
        return b.toString();
    }

    public List<Printable> printables() {
        return new ArrayList<Printable>(children());
    }

    public void print() {
        new Print().with(this);
    }

    public String pathOf(T t) {
        if (value.equals(t)) return "";
        StringBuilder b = new StringBuilder();
        if (right != null) b.append("1" + right.pathOf(t));
        else if (left != null) b.append("0" + left.pathOf(t));
        return b.toString();
    }

    public Integer height() {
        int rh = 0, lh = 0;
        if (right != null) rh = 1 + right.height();
        if (left != null) lh = 1 + left.height();
        return rh > lh ? rh : lh;
    }

    public void balance() {
        int r = 0, l = 0, d;
        if (right != null) r = right.height();
        if (left != null) l = left.height();
        d = r - l;
        if (Math.abs(d) <= 1) return;
        if (d > 0) rotateLeft();
        if (d < 0) rotateRight();

        for (Node<T> child : children()) {
            if (child != null) child.balance();
        }
    }

    private void rotateLeft() {
        Node<T> A = left;
        Node<T> B = right;
        Node<T> R = new Node<T>(value, B);
        R.left = A;
        A.parent = R;
        R.right = (B != null) ? B.left : null;

        if (isRChild()) parent.right = B;
        else if (isLChild()) parent.left = B;
        B.left = R;
    }

    private void rotateRight() {
        Node<T> A = left;
        Node<T> B = right;
        Node<T> R = new Node<T>(value, A);
        R.left = (A != null) ? A.right : null;
        R.right = B;

        if (isRChild()) parent.right = A;
        else if (isLChild()) parent.left = A;
        A.right = R;
    }

    private boolean isLChild() {
        return parent != null && parent.left != null && parent.left.equals(this);
    }

    private boolean isRChild() {
        return parent != null && parent.right != null && parent.right.equals(this);
    }
}

