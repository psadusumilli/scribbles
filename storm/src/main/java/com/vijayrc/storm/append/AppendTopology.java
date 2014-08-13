package com.vijayrc.storm.append;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import com.vijayrc.storm.MyTopology;

import static backtype.storm.utils.Utils.sleep;

public class AppendTopology implements MyTopology{
    private String name = "append";

    @Override
    public void run() {
        TopologyBuilder builder = new TopologyBuilder();

        //a spout with 2 executor threads (1 executor = 1 thread)
        builder.setSpout("s1", new WordSpout("s1"), 2);
        //a bolt with 3 executor threads with each thread running (6/3=2) tasks serially (running bolt instance)
        builder.setBolt("b1",new AppendBolt("b1"),3).setNumTasks(6).shuffleGrouping("s1");
        //a bolt with only 1 executor thread
        builder.setBolt("b2",new AppendBolt("b2"),1).shuffleGrouping("b1");


        Config config = new Config();
        config.setDebug(false);
        //2 worker process
        config.setNumWorkers(2);
        //combined parallelism = 2+3+1=6, 3 threads/process

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology(name,config,builder.createTopology());
        sleep(10000);
        cluster.killTopology(name);
        cluster.shutdown();
    }

    @Override
    public String name() {
        return name;
    }
}
