package com.tw.vrc.lists;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class QueueTest {
    @Test
    public void shouldWork() {
       Queue<String> q = new Queue<String>();
       q.enqueue("A");
       q.enqueue("B");
       q.enqueue("C");
       assertEquals("A",q.dequeue());
       assertEquals("B",q.dequeue());
       assertEquals("C",q.dequeue());
    }
}
