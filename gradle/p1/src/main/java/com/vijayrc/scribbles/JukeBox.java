package com.vijayrc.scribbles;

public class JukeBox {

    private MusicLibrary musicLibrary = new MusicLibrary();

    public String play(String name) {
        return  musicLibrary.play(name);
    }


}
