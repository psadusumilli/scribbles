package com.vijayrc.scribbles;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerTest {

    private Player player;

    @Before
    public void setUp() throws Exception {
        player = new Player();
    }

    @Test
    public void shouldSaySomething(){
        assertEquals("Georgia on my mind - Ray Charles", player.play());
    }
}
