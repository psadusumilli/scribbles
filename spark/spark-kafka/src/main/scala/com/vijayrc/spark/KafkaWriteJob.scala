package com.vijayrc.spark

import java.util.Properties

import kafka.producer.KeyedMessage
import org.apache.log4j.Logger
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.cloudera.spark.streaming.kafka.KafkaWriter._

/**
 * Created by vijayrc on 1/26/16.
 */
object KafkaWriteJob {

  def main(arg: Array[String]) {
    val logger = Logger.getLogger(this.getClass)

    val sc = new SparkContext(new SparkConf().setAppName("kafka-write-job"))
    logger.info("=> jobName =" + "kafka-write-job")

    val rdd: RDD[String] = sc.makeRDD(Seq("m1", "m2", "m3"))
    writeBatch(rdd)
  }


  def writeBatch(rdd: RDD[String]): Unit = {
    rdd.writeToKafka(producerProps, processingFunc)
  }

  def producerProps: Properties = {
    val props: Properties = new Properties()
    props.put("metadata.broker.list", "localhost:9092")
    props.put("serializer.class", "kafka.serializer.StringEncoder")
    //    props.put("key.serializer.class", "kafka.serializer.StringEncoder")
    //    props.put("partitioner.class", "example.producer.SimplePartitioner")
    props.put("request.required.acks", "1")
    props
  }

  def processingFunc: (String) => KeyedMessage[String, String] = {
    (x: String) => new KeyedMessage[String, String]("test", x) //topic is 'test'
    // (x: String) => new KeyedMessage[String,Array[Byte]]("default", "key-string",x.getBytes))
  }

}
