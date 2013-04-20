package com.tw.vrc.trees.avl;

import org.junit.*;

import java.util.Arrays;
import java.util.List;

public class AVLTree1Test {
    private AVLTree1<Integer> tree;

    @Before
    public void setUp() {
        tree = new AVLTree1<Integer>();
    }

    @Test
    public void shouldBalanceForRandomInput() {
        List<Integer> nos = Arrays.asList(1, 6, 3, 5, 8, 4, 2, 7, 10, 9, 8);
        for (Integer no : nos) {
            tree.insert(no);
        }
        tree.print();
    }

    @Test
    public void shouldNotBalanceForSortedInput() {
        for (int i = 0; i < 20; i++) {
            tree.insert(i);
        }
        tree.print();
    }
}
