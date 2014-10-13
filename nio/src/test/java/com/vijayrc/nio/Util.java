package com.vijayrc.nio;

import java.io.File;

public class Util {
    public static void print(Object objs){
        System.out.println(objs);
    }

    public static File resource(String path){
        return new File(Util.class.getResource(path).getFile());
    }
    public static File fromBaseDir(String path){
        return new File(System.getProperty("user.dir")+"/"+path);
    }
}
