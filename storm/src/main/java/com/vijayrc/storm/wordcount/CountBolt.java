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

import java.util.HashMap;
import java.util.Map;

public class CountBolt extends BaseRichBolt {
    private static Logger log = LogManager.getLogger(CountBolt.class);

    private Map<String, Integer> map = new HashMap<>();
    private OutputCollector myCollector;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.myCollector = collector;
    }

    @Override
    public void execute(Tuple tuple) {
        String word = tuple.getString(0);
        Integer count = map.get(word);
        if (count == null) count = 0;
        count++;
        map.put(word, count);
        log.info(word+":"+count);
        myCollector.emit(new Values(word,count));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word", "count"));
    }

    public void print(){
        map.keySet().forEach(k -> System.out.println(k+":"+map.get(k)));
    }
}
