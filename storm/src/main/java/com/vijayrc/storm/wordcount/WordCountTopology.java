package com.vijayrc.storm.wordcount;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import com.vijayrc.storm.MyTopology;

import static backtype.storm.utils.Utils.sleep;

public class WordCountTopology implements MyTopology {
    private String name = "wordcount";

    @Override
    public void run() throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("s1",new LineSpout(),1);
        builder.setBolt("b1",new SplitBolt(),10).shuffleGrouping("s1");

        CountBolt countBolt = new CountBolt();
        builder.setBolt("b2", countBolt,10).fieldsGrouping("b1",new Fields("word"));

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology(name, new Config(),builder.createTopology());
        sleep(20000);
        cluster.killTopology(name);
        cluster.shutdown();
    }

    @Override
    public String name() {
        return name;
    }
}
