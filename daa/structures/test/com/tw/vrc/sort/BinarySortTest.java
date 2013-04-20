package com.tw.vrc.sort;

import org.junit.*;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;

public class BinarySortTest {
    private BinarySort<Integer> heap;

    @Before
    public void setup() {
        heap = new BinarySort<Integer>();
    }

    @Test
    public void shouldWork() {
        heap.on(Arrays.asList(1, 6, 3, 5, 8, 4, 2, 9, 7));
        heap.print();
        assertEquals("123456789", heap.inOrder());

        heap.on(Arrays.asList(4, 6, 3, 5, 8, 1, 2, 9, 7));
        heap.print();
        assertEquals("123456789", heap.inOrder());
    }
}
