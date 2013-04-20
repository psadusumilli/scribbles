package com.tw.vrc.lists;

import org.junit.*;

import static junit.framework.Assert.assertEquals;

public class DoublyLinkedListTest {
    private DoublyLinkedList<String> list;

    @Before
    public void setup() {
        list = new DoublyLinkedList<String>();
    }

    @Test
    public void shouldAdd() {
        list.add("Cartman");
        list.add("Kyle");
        list.add("Kenny");
        assertEquals(new Integer(3), list.size());
        list.print();
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
