package com.tw.vrc.dictionaries;

import org.junit.*;

import static junit.framework.Assert.assertEquals;

public class ChainingMapTest {
    private ChainingMap<String, String> map;

    @Before
    public void setup() {
        map = new ChainingMap<String, String>();
    }

    @Test
    public void shouldWork() {
       map.put("C1","Kenny");
       map.put("C2","Kyle");
       map.put("C3","Cartman");
       map.put("C4","Butters");
       assertEquals("Kenny",map.find("C1"));
       map.put("C1","Token");
       assertEquals("Token",map.find("C1"));
    }
}
