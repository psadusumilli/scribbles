package com.vijayrc.storm;

import com.vijayrc.storm.append.AppendTopology;
import com.vijayrc.storm.wordcount.WordCountTopology;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

import static java.util.Arrays.asList;

public class AllTopologies {
    private static Logger log = LogManager.getLogger(AllTopologies.class);

    public static void main(String[] args) throws Exception {
        List<MyTopology> topologies = asList(new AppendTopology(), new WordCountTopology());
        if (args != null && args.length != 0) {
            MyTopology myTopology = topologies.stream().filter(t -> t.name().equals(args[0])).findFirst().get();
            log.info("running topology: "+myTopology.name());
            myTopology.run();
        } else log.warn("please enter a topology name");
        log.info("exit main.");
    }
}
