package com.tw.vrc.lists;

public class DoublyLinkedList<T extends Object> {
    private Node<T> head;
    private Node<T> tail;

    public void add(T t) {
        Node<T> node = new Node<T>(t);
        if (head == null) {
            head = node;
            tail = head;
            return;
        }
        node.prev = tail;
        tail.next = node;
        tail = node;
    }

    public void remove(T t) {
        Node<T> toRemove = new Node<T>(t);
        if (toRemove.equals(head)) {
            head = head.next;
            return;
        }
        for (Node<T> node = head; node != null; node = node.next) {
            if (toRemove.equals(node.next)) {
                Node<T> temp = node.next.next;
                node.next = temp;
                if (temp != null) temp.prev = node;
                break;
            }
        }
    }

    public Integer size() {
        int count = 0;
        for (Node<T> node = head; node != null; node = node.next)
            count++;
        return count;
    }

    public void print() {
        StringBuilder print = new StringBuilder();
        for (Node<T> node = head; node != null; node = node.next)
            print.append(node.value().toString() + "-");
        System.out.println("[" + print + "]");
    }
}
