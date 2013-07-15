package com.vijayrc.scribbles.radio.aggregate;

import com.vijayrc.scribbles.radio.domain.Play;
import com.vijayrc.scribbles.radio.domain.Subscriber;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PlayHistory {
    private List<Play> plays = new ArrayList<Play>();
    private Subscriber subscriber;

    public boolean hasNoSubscriber() {
        return subscriber == null;
    }
    public void addPlay(Play play) {
        this.plays.add(play);
    }
}

