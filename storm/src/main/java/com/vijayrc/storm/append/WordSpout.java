package com.vijayrc.storm.append;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.Random;

import static backtype.storm.utils.Utils.sleep;

public class WordSpout extends BaseRichSpout {
    private static Logger log = LogManager.getLogger(WordSpout.class);

    private SpoutOutputCollector myCollector;
    private String name;

    public WordSpout(String name) {
        this.name = name;
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }
    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        myCollector = collector;
    }
    @Override
    public void nextTuple() {
        sleep(100);
        final String[] words = new String[] {"cartman","stan","kyle","kenny"};
        final Random rand = new Random();
        final String word = words[rand.nextInt(words.length)]+"|"+name;
        log.info(word);
        myCollector.emit(new Values(word));
    }

}

