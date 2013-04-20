package com.tw.vrc.sort;

import java.util.List;

/**
 * Sort subsets which contains elements picked at intervals that reduce on each run.
 * Not stable
 * O(1) extra space
 * O(n^3/2) time as shown
 * Adaptive: O(n·lg(n)) time when nearly sorted
 * http://goanna.cs.rmit.edu.au/~stbird/Tutorials/ShellSort.html
 */
public class ShellSort<T extends Comparable> extends AbstractSort<T> {
    public void on(List<T> input) {
        int next;
        int gap = (int) (input.size() / 2.2);
        while (gap > 0) {
            for (int i = 0; i < input.size(); i++) {
                next = i + gap;
                if (next < input.size() && input.get(next).compareTo(input.get(i)) < 0) {
                    swap(input, next, i);
                }
            }
            gap--;
        }
    }
}
