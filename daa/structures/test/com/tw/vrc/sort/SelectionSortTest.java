package com.tw.vrc.sort;

import org.junit.Before;

public class SelectionSortTest extends SortTest {
    @Before
    public void setup(){
        plug(new SelectionSort());
    }
}
