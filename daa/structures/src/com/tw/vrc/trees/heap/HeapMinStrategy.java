package com.tw.vrc.trees.heap;

public class HeapMinStrategy<T extends Comparable> implements HeapStrategy {
    @Override
    public void balance(HeapNode node) {
        if (node.isMoreThan(node.right)) swapValues(node, node.right);
        if (node.isMoreThan(node.left)) swapValues(node, node.left);
    }

    private void swapValues(HeapNode<T> a, HeapNode<T> b) {
        T temp = a.value;
        a.value = b.value;
        b.value = temp;
    }
}
