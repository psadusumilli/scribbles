package com.vijayrc.scribbles;

import org.springframework.stereotype.Component;

@Component
public class HipHopMusic extends BaseMusic {

    public HipHopMusic() {
        super("hiphop");
    }

    @Override
    public String play() {
        return "Thrift Shop - MackleMore and Ryan";
    }
}
