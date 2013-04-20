package com.tw.vrc.trees.huffman;

import com.tw.vrc.trees.nodes.Node;

import java.util.ArrayList;
import java.util.List;

public class HuffmanTree<T extends Comparable> {

    private Node<T> root;

    public Node<T> on(List<T> list) {
        List<Node<T>> nodes = new ArrayList<Node<T>>();
        for (T t : list) {
            nodes.add(new Node(t));
        }
        recurse(nodes);
        return root;
    }

    private void recurse(List<Node<T>> nodes) {
        if (root != null) return;
        if (nodes.size() == 2) {
            root = new Node<T>();
            root.left = nodes.get(0);
            root.right = nodes.get(1);
            return;
        }
        List<Node<T>> subNodes = new ArrayList<Node<T>>();
        for (int i = 0; i + 1 < nodes.size(); i = i + 2) {
            Node<T> join = new Node<T>();
            join.left = nodes.get(i);
            join.right = nodes.get(i + 1);
            subNodes.add(join);
        }
        if(nodes.size() % 2 != 0) subNodes.add(nodes.get(nodes.size() - 1));
        recurse(subNodes);
    }

    public void print() {
        root.print();
    }

    public String pathOf(T t) {
        return root.pathOf(t);
    }

    public Node<T> root() {
        return root;
    }
}
