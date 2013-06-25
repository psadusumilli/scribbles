package com.vijayrc.scribbles;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by vchakrav on 6/25/13.
 */
public class HipHopMusicTest {

    private HipHopMusic hipHopMusic = new HipHopMusic();

    @Test
    public void shouldPlay() {
       assertEquals("Thrift Shop - MackleMore and Ryan", hipHopMusic.play());
    }
}
