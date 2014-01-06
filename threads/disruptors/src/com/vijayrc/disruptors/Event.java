package com.vijayrc.disruptors;

import com.lmax.disruptor.EventFactory;

public class Event {
    private String value;
    public void setValue(String value) {this.value = value;}
    @Override
    public String toString() {return value;}
    public final static EventFactory<Event> factory = new EventFactory<Event>() {
        public Event newInstance() { return new Event(); }
    };
}
