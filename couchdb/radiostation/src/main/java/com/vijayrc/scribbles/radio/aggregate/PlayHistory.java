package com.vijayrc.scribbles.radio.aggregate;

import com.vijayrc.scribbles.radio.domain.Play;
import com.vijayrc.scribbles.radio.domain.Subscriber;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static ch.lambdaj.Lambda.joinFrom;

@Getter
@Setter
public class PlayHistory {
    private List<Play> plays = new ArrayList<Play>();
    private Subscriber subscriber;

    public boolean hasNoSubscriber() {
        return subscriber == null;
    }
    public void addPlay(Play play) {
        this.plays.add(play);
    }
    @Override
    public String toString() {
        return subscriber +"\n"+joinFrom(plays,"\n").getSongId();
    }
}

