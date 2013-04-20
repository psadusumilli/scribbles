package com.vrc.neo.base;

public class Log {

    public static void print(String message){
        System.out.println(message);
    }

    public static void print(Exception e) {
        e.printStackTrace();
    }
}
