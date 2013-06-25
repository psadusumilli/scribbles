package com.vijayrc.scribbles;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JukeBoxTest {

    private JukeBox jukeBox;

    @Before
    public void setUp() throws Exception {
        jukeBox = new JukeBox();
    }

    @Test
    public void shouldPlaySelected(){
        assertEquals("Georgia on my mind - Ray Charles", jukeBox.play("jazz"));
        assertEquals("Thrift Shop - MackleMore and Ryan", jukeBox.play("hiphop"));
    }


}
