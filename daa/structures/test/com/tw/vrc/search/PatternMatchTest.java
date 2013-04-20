package com.tw.vrc.search;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class PatternMatchTest {
    @Test
    public void shouldFindPatternMatch() {
        PatternMatch patternMatch = new PatternMatch();
        assertEquals(new Integer(4), patternMatch.count("ABAABABBAB", "AB"));
        assertEquals(new Integer(3), patternMatch.count("ABAABABBABA", "ABA"));
        assertEquals(new Integer(1), patternMatch.count("ABAABABBABA", "BABA"));
        assertEquals(new Integer(1), patternMatch.count("ABAABABBABA", "ABAABABBABA"));
    }
}
