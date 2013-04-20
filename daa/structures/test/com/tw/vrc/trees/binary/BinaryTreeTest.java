package com.tw.vrc.trees.binary;

import com.tw.vrc.trees.nodes.Node;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.Assert.*;

public class BinaryTreeTest {
    private BinaryTree<Integer> tree;

    @Before
    public void setup() {
        tree = new BinaryTree<Integer>();
    }

    @Test
    public void shouldWork() {
        Node<Integer> root = tree.on(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        assertNotNull(root);
        tree.print();

        assertEquals(new Integer(1), tree.min());
        assertEquals(new Integer(9), tree.max());

        assertTrue(tree.exists(5));
        assertFalse(tree.exists(11));

        assertEquals("123456789", tree.inOrder());
        assertEquals("531248679", tree.preOrder());
        assertEquals("214376985", tree.postOrder());

        tree.insert(10);
        assertTrue(tree.exists(10));
        tree.print();

        assertEquals(new Integer(3), tree.height());
        tree.insert(11);
        tree.insert(12);
        tree.print();
        assertEquals(new Integer(5), tree.height());
    }

    @Test
    public void shouldReturnCount() {
        Node<Integer> root = tree.on(Arrays.asList(1, 2, 1, 3, 4, 5, 6, 1, 1, 7, 8, 9));
        assertNotNull(root);
        tree.print();
        assertEquals(new Integer(4),tree.occurrences(1));
    }

}
