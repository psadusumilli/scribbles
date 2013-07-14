package com.vijayrc.scribbles.radio.documents;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PlayHistory {
    private Subscriber subscriber;
    private List<Play> plays = new ArrayList<Play>();

    public boolean hasNoSubscriber() {
        return subscriber == null;
    }
    public void addPlay(Play play) {
        this.plays.add(play);
    }
}

