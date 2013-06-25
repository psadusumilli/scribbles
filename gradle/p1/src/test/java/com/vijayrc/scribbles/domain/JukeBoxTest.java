package com.vijayrc.scribbles.domain;

import com.vijayrc.scribbles.MusicLibrary;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class JukeBoxTest {

    private JukeBox jukeBox;

    @Mock
    private MusicLibrary musicLibrary;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        jukeBox = new JukeBox(musicLibrary);
    }

    @Test
    public void shouldPlaySelected(){
        when(musicLibrary.play("jazz")).thenReturn("jazz-song");
        when(musicLibrary.play("hiphop")).thenReturn("hiphop-song");

        assertEquals("jazz-song", jukeBox.play("jazz"));
        assertEquals("hiphop-song", jukeBox.play("hiphop"));
    }
}
