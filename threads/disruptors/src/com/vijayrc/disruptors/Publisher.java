package com.vijayrc.disruptors;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;

import static com.vijayrc.threads.util.Printer.log;

public class Publisher implements EventTranslator<Event>, Runnable {
    private Disruptor disruptor;
    private String name;
    private boolean stop;

    public Publisher(String name, Disruptor disruptor){
        this.name = name;
        this.disruptor = disruptor;
    }
    @Override
    public void translateTo(Event event, long sequence) {
        event.setValue(name+"-"+sequence+"|");
        log(event);
    }
    @Override
    public void run() {
        while (!stop)  {
            disruptor.publishEvent(this);
        }
    }
    public void stop(){stop = true;}
}
