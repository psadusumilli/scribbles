package com.tw.vrc.search;

import org.junit.Test;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;

public class BinarySearchTest {

    @Test
    public void shouldWork() {
        BinarySearch<String> bs = new BinarySearch<String>();
        assertEquals(new Integer(1), bs.find("B", Arrays.asList("A", "B", "C", "D")));
        assertEquals(new Integer(2), bs.find("C", Arrays.asList("A", "B", "C", "D")));
        assertEquals(new Integer(3), bs.find("D", Arrays.asList("A", "B", "C", "D")));
        assertEquals(new Integer(0), bs.find("A", Arrays.asList("A", "B", "C", "D")));
    }
}
