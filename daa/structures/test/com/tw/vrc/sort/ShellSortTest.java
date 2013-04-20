package com.tw.vrc.sort;
import org.junit.Before;

public class ShellSortTest extends SortTest{
    @Before
    public void setup(){
        plug(new ShellSort());
    }
}
