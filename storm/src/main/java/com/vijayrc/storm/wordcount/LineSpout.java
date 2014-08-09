package com.vijayrc.storm.wordcount;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.List;
import java.util.Map;

public class LineSpout extends BaseRichSpout {
    private static Logger log = LogManager.getLogger(LineSpout.class);

    private SpoutOutputCollector myCollector;
    private List<String> lines;
    private int count = 0;

    public LineSpout() throws Exception {
        File file = new File(getClass().getResource("/big.txt").getFile());
        this.lines = FileUtils.readLines(file);
        log.info("read lines:"+lines.size());
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }
    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.myCollector = collector;
    }
    @Override
    public void nextTuple() {
        String line = lines.get(count++);
        myCollector.emit(new Values(line));
    }
}
