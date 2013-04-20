package com.tw.vrc.lists;

public class LinkedList<T extends Object> {
    private Node<T> head;
    private Node<T> tail;

    public LinkedList() {
    }

    public void add(T t) {
        Node<T> item = new Node<T>(t);
        if (head == null) {
            head = item;
            tail = head;
            return;
        }
        tail.next = item;
        tail = item;
    }

    public void remove(T t) {
        Node<T> toRemove = new Node<T>(t);
        if (toRemove.equals(head)) head = head.next;
        for (Node<T> node = head; node != null;) {
            if (toRemove.equals(node.next)) {
                node.next = node.next.next;
                break;
            }
            node = node.next;
        }
    }

    public Integer size() {
        int count = 0;
        for (Node<T> node = head; node != null;) {
            count++;
            node = node.next;
        }
        return count;
    }

}
