package com.tw.vrc.lists;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class StackTest {

    @Test
    public void shouldWork() {
        Stack<String> stack = new Stack<String>();
        stack.push("A");
        stack.push("B");
        stack.push("C");
        assertEquals("C",stack.pop());
        assertEquals("B",stack.pop());
        assertEquals("A",stack.pop());
    }
}
