package com.tw.vrc.problems;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class DuplicateRemoverTest {
    @Test
    public void shouldWorkWithSort() {
        DuplicateRemover<String> remover = new DuplicateRemover<String>();
        List<String> actual = new ArrayList<String>();
        actual.addAll(Arrays.asList("A", "A", "B", "C", "X", "C", "B"));
        List<String> expected = new ArrayList<String>();
        expected.addAll(Arrays.asList("A", "B", "C", "X"));
        remover.on(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldWorkWithoutSort() {
        DuplicateRemover<String> remover = new DuplicateRemover<String>();
        List<String> actual = new ArrayList<String>();
        actual.addAll(Arrays.asList("A", "A", "B", "C", "X", "C", "B"));
        List<String> expected = new ArrayList<String>();
        expected.addAll(Arrays.asList("A", "B", "C", "X"));
        remover.on2(actual);
        assertEquals(expected, actual);
    }

}
