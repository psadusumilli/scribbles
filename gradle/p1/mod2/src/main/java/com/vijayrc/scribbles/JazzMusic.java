package com.vijayrc.scribbles;

public class JazzMusic extends BaseMusic{

    public JazzMusic() {
        super("jazz");
    }

    @Override
    public String play() {
        return "Georgia on my mind - Ray Charles";
    }
}
