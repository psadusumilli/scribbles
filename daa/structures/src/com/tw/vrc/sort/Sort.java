package com.tw.vrc.sort;

import java.util.List;

/**
 * Selection < Bubble < Insertion < Shell < Merge, Binary, Heap < Quick
 */
public interface Sort<T extends Comparable> {
    void on(List<T> input);
}
