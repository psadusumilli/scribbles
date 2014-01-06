package com.vijayrc.disruptors;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;

import static com.vijayrc.threads.util.Printer.log;

public class Publisher implements EventTranslator<Event>, Runnable {
    private Disruptor disruptor;
    private String value;
    private String name;
    private boolean stop;

    public Publisher(String name, Disruptor disruptor){
        this.name = name;
        this.disruptor = disruptor;
    }
    @Override
    public void translateTo(Event event, long sequence) {
        event.setValue(value);
        log("P|"+name+"|seq="+sequence+"|=>"+value);
    }
    @Override
    public void run() {
        long i = 1;
        while (!stop)  {
            value = "m-"+i++;
            disruptor.publishEvent(this);
        }
    }
    public void stop(){stop = true;}
}
