package com.vijayrc.storm;

public interface MyTopology {
    void run() throws Exception;
    String name();
}
