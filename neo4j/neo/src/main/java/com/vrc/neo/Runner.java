package com.vrc.neo;


import com.vrc.neo.base.NeoDb;
import com.vrc.neo.sample.Family;
import com.vrc.neo.sample.Sample;

import java.util.ArrayList;
import java.util.List;


public class Runner {
    private static List<Sample> samples = new ArrayList<Sample>();
    
    public static void main(String[] args) {
        NeoDb neoDb = new NeoDb().start();
        neoDb.hookToStop();
        samples.add(new Family(neoDb));
        samples.forEach(com.vrc.neo.sample.Sample::run);
    }

}
