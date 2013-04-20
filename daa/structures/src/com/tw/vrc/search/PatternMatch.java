package com.tw.vrc.search;

public class PatternMatch {
    //O(n*m)
    public Integer count(String input, String pattern) {
        int match = 0;
        int n = input.length();
        int p = pattern.length();
        for (int i = 0; i < n; i++) {
            if (input.charAt(i) == pattern.charAt(0)) {
                for (int j = 0; (i + j) < n && j < p; j++) {
                    if (input.charAt(i + j) != pattern.charAt(j)) break;
                    if (j == (p - 1)) match++;
                }
            }
        }
        return match;
    }
}
