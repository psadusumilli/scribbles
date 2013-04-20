package com.tw.vrc.sort;

import org.junit.Before;

public class QuickSortTest extends SortTest{
    @Before
    public void setup(){
        plug(new QuickSort());
    }
}
