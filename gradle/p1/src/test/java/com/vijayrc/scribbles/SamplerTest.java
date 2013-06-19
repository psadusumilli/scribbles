package com.vijayrc.scribbles;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SamplerTest {

    private Sampler sampler;

    @Before
    public void setUp() throws Exception {
        sampler = new Sampler();
    }

    @Test
    public void shouldSaySomething(){
        assertEquals("music",sampler.play());
    }
}
