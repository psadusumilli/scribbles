package com.vijayrc.spark

import java.util.Properties

import kafka.producer.KeyedMessage
import org.apache.log4j.Logger
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}
import org.cloudera.spark.streaming.kafka.KafkaWriter._

/**
 * Created by vijayrc on 1/26/16.
 */
object KafkaWriteJob {

  val logger = Logger.getLogger(this.getClass)

  def main(arg: Array[String]) {
    if (arg(0) == "stream")
      writeStream()
    else
      writeBatch()
  }

  def writeBatch(): Unit = {
    val sc = new SparkContext(new SparkConf().setAppName("kafka-write-job"))
    logger.info("=> jobName = kafka-write-job")

    val rdd: RDD[String] = sc.makeRDD(Seq("m1", "m2", "m3"))
    rdd.writeToKafka(producerProps, processingFunc)
  }

  def writeStream(): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("kafka-stream-write-job")
    logger.info("=> jobName = kafka-stream-write-job")
    val ssc = new StreamingContext(conf, Seconds(1)) // 1 sec batch
    val lines = ssc.socketTextStream("localhost", 7777)

    lines.writeToKafka(producerProps, processingFunc)

    ssc.start()
    ssc.awaitTermination()

  }

  def producerProps: Properties = {
    val props: Properties = new Properties()
    props.put("metadata.broker.list", "localhost:9092")
    props.put("serializer.class", "kafka.serializer.StringEncoder")
    props.put("request.required.acks", "1")
    props
  }

  def processingFunc: (String) => KeyedMessage[String, String] = {
    (x: String) => new KeyedMessage[String, String]("test", x) //topic is 'test'
    // (x: String) => new KeyedMessage[String,Array[Byte]]("default", "key-string",x.getBytes))
  }

}
