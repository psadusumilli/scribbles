package com.vijayrc.scribbles;

public class Player {

    private MusicLibrary musicLibrary = new MusicLibrary();

    public String play() {
        return  musicLibrary.playJazz();
    }


}
