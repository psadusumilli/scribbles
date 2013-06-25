package com.vijayrc.scribbles;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by vchakrav on 6/25/13.
 */
public class MusicLibraryTest {

    private MusicLibrary musicLibrary;
    @Mock
    private HipHopMusic hipHopMusic;
    @Mock
    private JazzMusic jazzMusic;

    @Before
    public void setup() {
        initMocks(this);
        musicLibrary = new MusicLibrary(hipHopMusic, jazzMusic);
    }

    @Test
    public void shouldPlayJazzMusic() {
        when(jazzMusic.play()).thenReturn("jazz-song");
        when(jazzMusic.is("jazz")).thenReturn(true);

        assertEquals("jazz-song", musicLibrary.play("jazz"));
    }
}
