package com.vijayrc.scribbles.radio.data;

import lombok.extern.log4j.Log4j;

import java.util.concurrent.Callable;

@Log4j
public class DataSetupTask implements Callable {
    private DataSetupMethod method;

    public DataSetupTask(DataSetupMethod method) {
        this.method = method;
    }

    @Override
    public Object call() throws Exception {
        log.info("started:" + method.description() + "|" + method.order());
        method.run();
        return "completed:" + method.description() + "|" + method.order();
    }
}
