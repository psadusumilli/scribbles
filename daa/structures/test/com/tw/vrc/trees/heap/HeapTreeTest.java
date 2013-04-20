package com.tw.vrc.trees.heap;

import org.junit.*;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class HeapTreeTest {
    private HeapTree<Integer> tree;

    @Test
    public void shouldWorkForMax() {
        HeapMaxStrategy strategy = new HeapMaxStrategy();
        tree = new HeapTree<Integer>(strategy);
        List<Integer> list = Arrays.asList(1, 6, 13, 3, 5, 8, 14, 4, 2, 9, 7, 10, 15, 11, 12, 0);
        for (Integer item : list) {
            tree.add(item);
        }
        tree.print();
        assertEquals(new Integer(15), tree.remove());
        assertEquals(new Integer(14), tree.remove());
        assertEquals(new Integer(13), tree.remove());
        assertEquals(new Integer(12), tree.remove());
        tree.print();
    }

    @Test
    public void shouldWorkForMin() {
        HeapMinStrategy strategy = new HeapMinStrategy();
        tree = new HeapTree<Integer>(strategy);
        List<Integer> list = Arrays.asList(1, 6, 13, 3, 5, 8, 14, 4, 2, 9, 7, 10, 15, 11, 12);
        for (Integer item : list) {
            tree.add(item);
        }
        tree.print();
        assertEquals(new Integer(1), tree.remove());
        assertEquals(new Integer(2), tree.remove());
        assertEquals(new Integer(3), tree.remove());
        assertEquals(new Integer(4), tree.remove());
        tree.print();
    }

    @Test
    public void shouldReturnLastLeafValue() {
        HeapMaxStrategy strategy = new HeapMaxStrategy();
        tree = new HeapTree<Integer>(strategy);
        List<Integer> list = Arrays.asList(1, 4, 3, 2, 7);
        for (Integer item : list) {
            tree.add(item);
        }
        tree.print();
        assertEquals(new Integer(7), tree.remove());
        assertEquals(new Integer(4), tree.remove());
        tree.print();
        assertEquals(new Integer(3), tree.remove());
        tree.print();
        assertEquals(new Integer(2), tree.remove());
        assertEquals(new Integer(1), tree.remove());
        tree.print();
    }
}
