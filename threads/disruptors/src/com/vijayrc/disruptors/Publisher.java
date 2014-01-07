package com.vijayrc.disruptors;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;

import static com.vijayrc.threads.util.Printer.log;

public class Publisher implements EventTranslator<Event>, Runnable {
    private Disruptor disruptor;
    private String name;
    private long count;
    private boolean stop;

    public Publisher(String name, long count, Disruptor disruptor){
        this.name = name;
        this.count = count;
        this.disruptor = disruptor;
    }
    @Override
    public void translateTo(Event event, long sequence) {
        event.setValue(name+"-"+sequence+"|");
        log(event);
    }
    @Override
    public void run() {
        int i=0;
        while (count >= i && !stop )  {
            disruptor.publishEvent(this);
            ++i;
        }
    }
    public void stop(){stop = true;}
}
