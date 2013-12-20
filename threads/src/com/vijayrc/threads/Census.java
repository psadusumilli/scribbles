package com.vijayrc.threads;

import static com.vijayrc.threads.Printer.log;

/**
 * Thread.activeCount/enumerate gives all threads belonging to the same threadgroup as calling thread,
 * which in our case is 'main'
 * output is Thread[thread-name,thread-priority,thread-group]
 */
public class Census {
    public static void main(String[] args) {
        Thread[] threads = new Thread[Thread.activeCount()];
        int n = Thread.enumerate(threads);
        for (int i = 0; i < n; i++)
            log(threads[i]);
    }
}
