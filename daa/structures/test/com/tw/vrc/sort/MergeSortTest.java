package com.tw.vrc.sort;
import org.junit.*;

public class MergeSortTest extends SortTest{
    @Before
    public void setup(){
       plug(new MergeSort());
    }
}
