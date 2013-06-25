package com.vijayrc.scribbles;

import java.util.Arrays;
import java.util.List;

public class MusicLibrary {

    private List<BaseMusic> musicList = Arrays.asList(new JazzMusic(), new HipHopMusic()) ;

    public String play(String name) {
        for (Music music : musicList)
            if(music.is(name)) return music.play();
        return "";
    }


}
