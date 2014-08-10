package com.vijayrc.storm.append;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import com.vijayrc.storm.append.MyBolt;
import com.vijayrc.storm.append.MySpout;

import static backtype.storm.utils.Utils.sleep;

public class MyTopology {
    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("s1", new MySpout("s1"), 2);
        builder.setBolt("b1",new MyBolt("b1"),3).shuffleGrouping("s1");
        builder.setBolt("b2",new MyBolt("b2"),1).shuffleGrouping("b1");

        Config config = new Config();
        config.setDebug(false);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("myTopology",config,builder.createTopology());
        sleep(10000);
        cluster.killTopology("myTopology");
        cluster.shutdown();
    }
}
