package com.tw.vrc.sort;
import org.junit.Before;

public class InsertionSortTest extends SortTest{
    @Before
    public void setup(){
        plug(new InsertionSort());
    }
}
