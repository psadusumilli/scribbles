package com.vijayrc.scribbles;

import org.springframework.stereotype.Component;

@Component
public class JazzMusic extends BaseMusic{

    public JazzMusic() {
        super("jazz");
    }

    @Override
    public String play() {
        return "Georgia on my mind - Ray Charles";
    }
}
