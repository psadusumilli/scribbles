package com.tw.vrc.trees.bottom;


import com.tw.vrc.trees.nodes.BigNode;
import com.tw.vrc.utilities.Print;

import java.util.ArrayList;
import java.util.List;

public class BottomUpTree<T extends Comparable> {
    private BigNode<T> root;
    private Integer leaves;

    public BottomUpTree(Integer leaves) {
        this.leaves = leaves;
    }

    public BigNode<T> on(List<T> list) {
        List<BigNode<T>> nodes = new ArrayList<BigNode<T>>();
        for (int i = 0; i < list.size(); i++)
            nodes.add(new BigNode<T>(list.get(i)));
        recurse(nodes);
        return root;
    }

    private void recurse(List<BigNode<T>> nodes) {
        List<BigNode<T>> subNodes = new ArrayList<BigNode<T>>();
        if (nodes.size() <= leaves) {
            root = new BigNode<T>();
            for (BigNode<T> node : nodes) root.addChild(node);
            return;
        }
        for (int i = 0; (i + leaves) <= nodes.size(); i = i + leaves) {
            BigNode<T> join = new BigNode<T>();
            for (int j = 0; j < leaves; j++) {
                join.addChild(nodes.get(i + j));
            }
            subNodes.add(join);
        }
        if (nodes.size() % leaves != 0)
            subNodes.add(nodes.get(nodes.size() - 1));
        recurse(subNodes);
    }

    public void print() {
       new Print().with(root);
    }


}
