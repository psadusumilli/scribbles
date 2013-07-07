package com.vijayrc.scribbles.radio.util;

import lombok.extern.log4j.Log4j;

import java.util.Map;

@Log4j
public class Print {

    public static void the(Map map) {
        String str = "\n";
        for (Object key : map.keySet())
            str = str + key + "=>" + map.get(key) + "\n";
        log.info(str);
    }

    public static void the(Iterable items) {
        String str = "\n";
        for (Object item : items)
            str = str + item + "\n";
        log.info(str);
    }
}
