package com.tw.vrc.trees.bottom;

import com.tw.vrc.trees.nodes.BigNode;
import org.junit.*;

import java.util.Arrays;

import static junit.framework.Assert.assertNotNull;

public class BottomUpTreeTest {
    private BottomUpTree<String> tree;

    @Before
    public void setup() {
        tree = new BottomUpTree<String>(3);
    }

    @Test
    public void shouldWork() {
        BigNode<String> root = tree.on(Arrays.asList("Bebe", "Butters", "Cartman", "Clyde",
                "Jimmy", "Kenny", "Kyle", "Stan",
                "Timmy", "Token", "Wendy"));
        assertNotNull(root);
        tree.print();
    }
}
