package com.tw.vrc.sort;

import java.util.List;

/**
 * gist - swap neighbouring elements starting from left
 * sorted subsets grow from left
 * insert the immediate left element into sorted subset
 * start swapping the adjacent elements of subset from right end.
 *
 * Stable
 * O(1) extra space
 * O(n2) comparisons and swaps
 * Adaptive: O(n) time when nearly sorted
 * Very low overhead
 */
public class InsertionSort<T extends Comparable> extends AbstractSort<T>{

    public void on(List<T> input) {
        for (int i = 0; i < input.size(); i++)
            for (int j = i; j > 0; j--)
                if (input.get(j).compareTo(input.get(j - 1)) < 0)
                    swap(input, j, j - 1);
    }

}
