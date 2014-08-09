package com.vijayrc.storm.wordcount;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Map;

public class SplitBolt extends BaseRichBolt{
    private static Logger log = LogManager.getLogger(SplitBolt.class);

    private OutputCollector myCollector;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.myCollector = collector;
    }

    @Override
    public void execute(Tuple tuple) {
        String line = tuple.getString(0);
        for (String split : line.split("\\s"))
            myCollector.emit(new Values(split));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }
}
