package com.tw.vrc.sort;

import java.util.List;

public class QuickSort<T extends Comparable> extends AbstractSort<T> {

    @Override
    public void on(List<T> input) {
        quickie(input);
    }

    private void quickie(List<T> input) {
        if (input.size() < 2) return;
        int m = input.size() / 2;
        T mid = input.get(m);
        int l = 0, r = input.size() - 1;
        while (l <= r) {
            while (input.get(l).compareTo(mid) < 0) l++;
            while (input.get(r).compareTo(mid) > 0) r--;
            if (l <= r) {
                swap(input, l, r);
                l++;
                r--;
            }
        }
        if (l >= input.size()) return;
        quickie(input.subList(0, l));
        quickie(input.subList(l, input.size()));
    }
}
