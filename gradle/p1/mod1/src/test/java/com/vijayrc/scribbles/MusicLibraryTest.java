package com.vijayrc.scribbles;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by vchakrav on 6/25/13.
 */
public class MusicLibraryTest {

    private MusicLibrary musicLibrary;

    @Before
    public void setup() {
        musicLibrary = new MusicLibrary();
    }

    @Test
    public void shouldPlayJazzMusic() {
        assertEquals("Georgia on my mind - Ray Charles", musicLibrary.playJazz());
    }
}
