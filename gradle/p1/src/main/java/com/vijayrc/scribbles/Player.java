package com.vijayrc.scribbles;

import com.vijayrc.gradle.MusicLibrary;

public class Player {

    private MusicLibrary musicLibrary = new MusicLibrary();

    public String play() {
        return  musicLibrary.playJazz();
    }


}
