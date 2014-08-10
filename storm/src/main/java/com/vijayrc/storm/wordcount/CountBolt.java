package com.vijayrc.storm.wordcount;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.vijayrc.storm.redis.JedisRepo;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class CountBolt extends BaseRichBolt {
    private static Logger log = LogManager.getLogger(CountBolt.class);
    private JedisRepo repo;
    private OutputCollector myCollector;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.repo = new JedisRepo();
        this.myCollector = collector;
        log.info("repo init");
    }
    @Override
    public void execute(Tuple tuple) {
        String word = tuple.getString(0);
        Jedis jedis = repo.newJedis();
        jedis.zincrby("counts", 1, word);
        repo.close(jedis);

        myCollector.ack(tuple);
    }
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    }

    @Override
    public void cleanup() {
//        repo.shutdown();
        super.cleanup();
        log.info("repo shutdown");
    }
}
