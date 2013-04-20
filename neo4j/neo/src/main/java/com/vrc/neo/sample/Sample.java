package com.vrc.neo.sample;

import com.vrc.neo.base.NeoDb;

public abstract class Sample {

    protected NeoDb neoDb;

    protected Sample(NeoDb neoDb) {
        this.neoDb = neoDb;
    }

    public abstract void run();
}
