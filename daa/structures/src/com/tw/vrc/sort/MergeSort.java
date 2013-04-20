package com.tw.vrc.sort;

import java.util.ArrayList;
import java.util.List;

/**
 * Stable
 * O(n) extra space for arrays (as shown)
 * O(lg(n)) extra space for linked lists
 * O(n·lg(n)) time
 * Not adaptive
 * Does not require random access to data
 */
public class MergeSort<T extends Comparable> extends AbstractSort<T> {

    public void on(List<T> input) {
        mergeSort(input);
    }

    private void mergeSort(List<T> input) {
        int size = input.size();
        if (size < 2) return;
        int mid = size / 2;

        mergeSort(input.subList(0, mid));
        mergeSort(input.subList(mid, size));

        List<T> A = input.subList(0, mid);
        List<T> B = input.subList(mid, size);
        List<T> C = new ArrayList<T>();
        int c1 = 0, c2 = 0;
        while (c1 < A.size() && c2 < B.size()) {
            T a = A.get(c1);
            T b = B.get(c2);
            if (a.compareTo(b) > 0) {
                C.add(b);
                c2++;
            } else {
                C.add(a);
                c1++;
            }
        }
        if (c1 < A.size()) C.addAll(A.subList(c1, A.size()));
        if (c2 < B.size()) C.addAll(B.subList(c2, B.size()));
        for (int i = 0; i < size; i++) {
            input.set(i, C.get(i));
        }
    }

}
