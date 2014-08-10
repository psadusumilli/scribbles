package com.vijayrc.storm.wordcount;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import com.vijayrc.storm.MyTopology;
import com.vijayrc.storm.redis.JedisRepo;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;

import static backtype.storm.utils.Utils.sleep;

public class WordCountTopology implements MyTopology {
    private static Logger log = LogManager.getLogger(WordCountTopology.class);

    private String name = "wordcount";

    @Override
    public void run() throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("s1",new LineSpout(),1);
        builder.setBolt("b1",new SplitBolt(),10).shuffleGrouping("s1");
        builder.setBolt("b2", new CountBolt(),10).fieldsGrouping("b1",new Fields("word"));

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology(name, new Config(),builder.createTopology());
        sleep(10000);
        cluster.killTopology(name);
        cluster.shutdown();

        readResults();
    }

    @Override
    public String name() {
        return name;
    }

    private void readResults() {
        log.info("reading results...");
        JedisRepo repo = new JedisRepo();
        Jedis jedis = repo.newJedis();
        log.info("no of words: " + jedis.zcard("counts"));
        jedis.zrangeWithScores("counts", 0, 10).stream().forEach(t -> log.info(t.getElement() + "->" + t.getScore()));
        repo.close(jedis);
        repo.shutdown();
    }
}
