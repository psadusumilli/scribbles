package com.vijayrc.spark

import kafka.serializer.StringDecoder
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.kafka.{HasOffsetRanges, KafkaUtils, OffsetRange}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by vijayrc on 12/26/15.
 */
object SampleKafkaJob {

  /**
   * run all samples
   */
  def main(args: Array[String]): Unit = {
    if (args.nonEmpty && args {
      0
    }.equalsIgnoreCase("stream")) runStreamJob()
    else runBatchJob()
  }


  /**
   * to create DirectKafkaInputDStream, a stream of batches.
   * Each batch corresponds to a KafkaRDD
   */
  def runStreamJob(): Unit = {
    val ssc = new StreamingContext(new SparkConf, Seconds(5))
    val kafkaParams = Map("metadata.broker.list" -> "localhost:9092") // hostname:port for kafka brokers, not zookeeper; host1:port1, host2:port2
    val topics = Set("test") // topic1, topic2
    val stream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topics)

    stream.foreachRDD { rdd =>
      showRDDdetails(rdd)
    }

    def showRDDdetails(rdd: RDD[(String, String)]) = {

      //show messages
      rdd.values.foreach(println)

      //show offset details
      val offsets: Array[OffsetRange] = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
      rdd.mapPartitionsWithIndex { (i, iter) => // index to get the correct offset range for the rdd partition we're working on
        val osr: OffsetRange = offsets(i)
        println(osr.topic + "|" + osr.partition + "|" + osr.fromOffset + "-" + osr.untilOffset)
        Iterator.empty
      }
    }

  }

  /**
   * to create kafkaRDDs from fixed batch windows in topics
   */
  def runBatchJob(): Unit = {
    val sc = new SparkContext(new SparkConf)
    val kafkaParams = Map("metadata.broker.list" -> "localhost:9092")
    val offsetRanges = Array(
      OffsetRange("test", 0, 1, 3) //topic name, partition, start-offset, end-offset
    )

    val rdd = KafkaUtils.createRDD[String, String, StringDecoder, StringDecoder](sc, kafkaParams, offsetRanges)
    rdd.values.foreach(println)
  }
}
