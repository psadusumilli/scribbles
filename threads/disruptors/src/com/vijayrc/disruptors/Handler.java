package com.vijayrc.disruptors;

import com.lmax.disruptor.EventHandler;

import static com.vijayrc.threads.util.Printer.log;

public class Handler<Event> implements EventHandler<Event> {
    private String name;
    public Handler(String name) {this.name = name;}

    @Override
    public void onEvent(Event event, long sequence, boolean batchEnd) throws Exception {
        log("H|"+name+"|seq="+sequence+"|=>"+event);
    }
}
