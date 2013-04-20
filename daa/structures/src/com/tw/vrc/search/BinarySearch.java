package com.tw.vrc.search;

import java.util.List;

/**
 *
 * O(log(n) - base 2)
 */
public class BinarySearch<T extends Comparable> {

    public Integer find(T t, List<T> input) {
        return realFind(t, input, 0, input.size() - 1);
    }

    private Integer realFind(T t, List<T> input, int start, int end) {
        if (t.compareTo(input.get(start)) == 0) return start;
        if (t.compareTo(input.get(end)) == 0) return end;

        int mid = (end + start) / 2;
        int compare = t.compareTo(input.get(mid));
        if (compare < 0) return realFind(t, input, start, mid);
        if (compare == 0) return mid;
        if (compare > 0) return realFind(t, input, mid + 1, end);
        return -1;
    }
}
