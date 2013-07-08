package com.vijayrc.scribbles.radio.seed.base;

import lombok.extern.log4j.Log4j;

import java.util.concurrent.Callable;

@Log4j
public class SeedTask implements Callable<String> {
    private SeedMethod method;

    public SeedTask(SeedMethod method) {
        this.method = method;
    }

    @Override
    public String call() throws Exception {
        log.info("started:" + method);
        method.run();
        return "completed:" + method;
    }
}
