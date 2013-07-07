package com.vijayrc.scribbles.radio.seed;

import lombok.Getter;

@Getter
public class SeedRange {
    private final int countries = 20;
    private final int states_min = 30;
    private final int states_max = 50;
    private final int cities_min = 100;
    private final int cities_max = 200;

    //pick random location and assign to artist
    private final int artists = 500;

    //pick random artist and assign to song
    private final int songs = 10000;
    private final int songs_per_album_min = 5;
    private final int songs_per_album_max = 10;

    //pick random location and assign to subscriber
    private final int subscribers = 20000;

    //pick random song and assign to subscriber play history
    private final int songs_per_player_min = 1;
    private final int songs_per_player_max = 100;



}
