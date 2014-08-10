package com.vijayrc.storm.append;


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

public class MyBolt extends BaseRichBolt {
    private static Logger log = LogManager.getLogger(MyBolt.class);

    private String name;
    private OutputCollector myCollector;

    public MyBolt(String name) {
        this.name = name;
    }
    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.myCollector = collector;
    }
    @Override
    public void execute(Tuple tuple) {
        String value = tuple.getString(0)+"|"+name;
        log.info(value);
        myCollector.emit(tuple,new Values(value));
        myCollector.ack(tuple);
    }
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }
}
