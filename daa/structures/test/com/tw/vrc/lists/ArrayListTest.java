package com.tw.vrc.lists;

import org.junit.*;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;

public class ArrayListTest {
    private ArrayList<String> list;

    @Before
    public void setup(){
        list = new ArrayList<String>(2);
    }

    @Test
    public void shouldAddElement() {
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        assertEquals(new Integer(4),list.size());
    }
}
