package com.tw.vrc.sort;

import org.junit.*;
public class BubbleSortTest extends SortTest{
    @Before
    public void setup(){
        plug(new BubbleSort());
    }
}
