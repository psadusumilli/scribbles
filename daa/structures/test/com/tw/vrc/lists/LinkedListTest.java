package com.tw.vrc.lists;

import org.junit.*;

import static junit.framework.Assert.assertEquals;

public class LinkedListTest {

    private LinkedList<String> list;

    @Before
    public void setup() {
        list = new LinkedList<String>();
    }

    @Test
    public void shouldAddItem() {
        list.add("Cartman");
        list.add("Kyle");
        list.add("Kenny");
        assertEquals(new Integer(3), list.size());
    }

    @Test
    public void shouldRemoveItem() {
        list.add("Cartman");
        list.add("Kyle");
        list.add("Kenny");

        list.remove("Kyle");
        assertEquals(new Integer(2), list.size());

        list.add("Jimmy");
        list.add("Butters");

        list.remove("Cartman");
        assertEquals(new Integer(3), list.size());
        list.remove("Butters");
        assertEquals(new Integer(2), list.size());

    }
}
