package com.tw.vrc.trees.huffman;

import org.junit.*;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class HuffmanTreeTest {

    private HuffmanTree<String> tree;

    @Before
    public void setup(){
        tree = new HuffmanTree<String>();
        tree.on(Arrays.asList("Bebe", "Butters", "Cartman", "Clyde",
                "Jimmy", "Kenny", "Kyle", "Stan",
                "Timmy", "Token", "Wendy"));
    }

    @Test
    public void shouldWork() {
        assertNotNull(tree.root());
        tree.print();
    }

}
