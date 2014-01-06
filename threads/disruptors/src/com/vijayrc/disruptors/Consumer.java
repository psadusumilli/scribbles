package com.vijayrc.disruptors;

import com.lmax.disruptor.EventHandler;

import static com.vijayrc.disruptors.Printer.log;

public class Consumer<Event> implements EventHandler<Event> {
    @Override
    public void onEvent(Event event, long sequence, boolean batchEnd) throws Exception {
        log("seq="+sequence+"|event="+event);
    }
}
