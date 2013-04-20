package com.tw.vrc.sort;

import java.util.List;

/**
 * Stable
 * O(1) extra stack space
 * O(n2) comparisons and swaps
 * Adaptive: O(n) when nearly sorted
 * Slightly more overhead than InsertionSort
 * Flag 'swapped' is good bcos it can break loop if remaining subset is already sorted
 */
public class BubbleSort<T extends Comparable> extends AbstractSort<T> {

    public void on(List<T> input) {
        boolean swapped;
        for (int i = 0; i < input.size(); i++) {
            swapped = false;
            for (int j = input.size() - 1; j != i; j--) {
                if (input.get(j).compareTo(input.get(j - 1)) < 0) {
                    swap(input, j, j - 1);
                    swapped = true;
                }

            }
            if (!swapped) break;
        }
    }
}
