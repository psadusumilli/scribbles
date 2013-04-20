package com.tw.vrc.lists;

public class Stack<T extends Object> {
    private Node<T> tail;

    public void push(T t) {
        Node<T> node = new Node<T>(t);
        node.next = tail;
        tail = node;
    }

    public T pop() {
        Node<T> toPop = tail;
        if (tail != null) {
            tail = tail.next;
            return toPop.value();
        }
        return null;
    }

}
