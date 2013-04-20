package com.tw.vrc.sort;

import org.junit.*;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class HeapSortTest {
    private HeapSort<Integer> sort;

    @Before
    public void setup() {
        sort = new HeapSort<Integer>();
    }

    @Test
    public void shouldWork() {
        List<Integer> input = Arrays.asList(1, 5, 7, 8, 3, 4, 2, 6, 9, 11, 12, 10);
        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), sort.asc(input));
        assertEquals(Arrays.asList(12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1), sort.desc(input));
    }
}
