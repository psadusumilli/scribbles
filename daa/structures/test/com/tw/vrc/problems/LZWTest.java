package com.tw.vrc.problems;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class LZWTest {
    @Test
    public void shouldWork() {
        LZW lzw = new LZW();
        String input = "BABAABAZBABAABAZ";
        lzw.compress(input);
        assertNotNull(lzw.codes());
    }

}