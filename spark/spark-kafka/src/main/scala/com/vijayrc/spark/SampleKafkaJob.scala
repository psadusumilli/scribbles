package com.vijayrc.spark

import kafka.serializer.StringDecoder
import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.streaming.kafka.{OffsetRange, KafkaUtils}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * Created by vijayrc on 12/26/15.
 */
object SampleKafkaJob {

  def main(args: Array[String]): Unit = {

    runStreamJob()

  }

  /**
   *  to create DirectKafkaInputDStream, a stream of batches.
   *  Each batch corresponds to a KafkaRDD
   */
  def runStreamJob(): Unit = {
    val ssc = new StreamingContext(new SparkConf, Seconds(60))

    val kafkaParams = Map("metadata.broker.list" -> "localhost:9092") // hostname:port for kafka brokers, not zookeeper; host1:port1, host2:port2
    val topics = Set("test") // topic1, topic2

    val stream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topics)
  }

  /**
   *  to create kafkaRDDs from fixed batch windows in topics
   */
  def runBatchJob(): Unit = {
    val sc = new SparkContext(new SparkConf)

    val kafkaParams = Map("metadata.broker.list" -> "localhost:9092")

    val offsetRanges = Array(
      OffsetRange("test", 0, 1, 3) //topic name, partition, start-offset, end-offset
    )

    val rdd = KafkaUtils.createRDD[String, String, StringDecoder, StringDecoder]( sc, kafkaParams, offsetRanges)
  }
}
