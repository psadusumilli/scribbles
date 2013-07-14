package com.vijayrc.scribbles.radio.documents;

import lombok.Data;

import java.util.List;

@Data
public class SubscriberAndPlays {
    private Subscriber subscriber;
    private List<Play> plays;
}
