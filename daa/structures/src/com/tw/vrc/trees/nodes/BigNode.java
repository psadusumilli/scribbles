package com.tw.vrc.trees.nodes;

import com.tw.vrc.utilities.Print;
import com.tw.vrc.utilities.Printable;

import java.util.ArrayList;
import java.util.List;

public class BigNode<T extends Comparable> implements Printable {
    private T value;
    private List<BigNode> children = new ArrayList<BigNode>();

    public BigNode() {
    }

    public BigNode(T value) {
        this.value = value;
    }

    public void addChild(BigNode node) {
        children.add(node);
    }

    public T value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BigNode node = (BigNode) o;
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

    public boolean exists(BigNode<T> toFind) {
        if (this.equals(toFind)) return true;
        for (BigNode child : children) {
            if (child.exists(toFind)) return true;
        }
        return false;
    }

    public List<Printable> printables() {
        return new ArrayList<Printable>(children);
    }

    public void print() {
        new Print().with(this);
    }
}
