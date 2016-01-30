package com.vijayrc.spark

import java.io.ByteArrayOutputStream
import java.util

import kafka.serializer.DefaultDecoder
import org.apache.avro.io.{DecoderFactory, EncoderFactory}
import org.apache.avro.specific.{SpecificDatumReader, SpecificDatumWriter}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Minutes, Seconds, StreamingContext}

import scala.util.Random

/**
 * Created by vijayrc on 1/28/16.
 */
object KafkaAvroReadJob {

  val logger = Logger.getLogger(this.getClass)

  /**
   * run all samples
   */
  def main(args: Array[String]): Unit = {
    if (args.nonEmpty && args {
      0
    }.equalsIgnoreCase("write")) writeAvro()
    else readAvro()
  }

  /**
   * write an avro event to kafka
   */
  def writeAvro(): Unit = {

    val props = new util.HashMap[String, Object]()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer")
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    val producer = new KafkaProducer[String, Array[Byte]](props)

    for (i <- 0 to 50) {
      val clickBytes = serializeClickEvent(newRandomClickEvent)
      val message = new ProducerRecord[String, Array[Byte]]("test", null, clickBytes)
      producer.send(message)
      println("sent message" + i)
      Thread sleep 1000
    }

    def newRandomClickEvent: ClickEvent = {
      val userId = Random.nextInt(5)
      val productId = Random.nextInt(5)
      println("click: " + userId+"->"+productId)
      new ClickEvent(userId, productId)
    }

    /**
     * object to be serialized via Avro
     */
    def serializeClickEvent(clickEvent: ClickEvent): Array[Byte] = {
      val out = new ByteArrayOutputStream()
      val encoder = EncoderFactory.get.binaryEncoder(out, null)
      val writer = new SpecificDatumWriter[ClickEvent](ClickEvent.getClassSchema)

      writer.write(clickEvent, encoder)
      encoder.flush()
      out.close()
      out.toByteArray
    }

  }

  /**
   * read an avro event to kafka
   */
  def readAvro(): Unit = {
    val sparkConf = new SparkConf().setAppName("click-count").setMaster("local[2]")

    val ssc = new StreamingContext(sparkConf, Seconds(2))
    ssc.checkpoint("./checkpointDir")

    val kafkaConf = Map("metadata.broker.list" -> "localhost:9092",
      "zookeeper.connect" -> "localhost:2181",
      "group.id" -> "kafka-spark-streaming-example",
      "zookeeper.connection.timeout.ms" -> "1000")

    val topicMap = Map("test" -> 1)
    val userNameMapRDD = ssc.sparkContext.parallelize(Array((1, "Joe"), (2, "Michelle"), (3, "David"), (4, "Anthony"), (5, "Lisa")))
    val productNameMapRDD = ssc.sparkContext.parallelize(Array((1, "Legos"), (2, "Books"), (3, "Board Games"), (4, "Food"), (5, "Computers")))

    // read from topic
    val lines = KafkaUtils.createStream[String, Array[Byte], DefaultDecoder, DefaultDecoder](ssc, kafkaConf, topicMap, StorageLevel.MEMORY_ONLY_SER).map(_._2)

    //  deserialize clickevent => userid, prodid => username, prodid
    val mappedUserName = lines.transform {
      rdd =>
      val clickRDD: RDD[(Int, Int)] = rdd.map { bytes => AvroUtil.clickEventDecode(bytes) }.map { clickEvent =>
        (clickEvent.getUserId: Int) -> clickEvent.getProductId
      }
      clickRDD.join(userNameMapRDD).map { case (userId, (productId, userName)) => (userName, productId) } // Joe->1,Anthony->3
    }

    // username, prodid => username, prodname
    val mappedProductId = mappedUserName.transform { rdd =>
      val productRDD = rdd.map { case (userName, productId) => (productId: Int, userName) }
      productRDD.join(productNameMapRDD).map { case (productId, (productName, userName)) => (userName, productName) } //Joe -> Legos, Anthony -> BoardGames
    }

    // get a count of all the users and the products they visited in the last 10 minutes, refreshing every 2 seconds
    val clickCounts = mappedProductId.map(x => (x, 1L))
      .reduceByKeyAndWindow(_ + _, _ - _, Minutes(10), Seconds(2), 2)
      .map { case ((productName, userName), count) =>   (userName, productName, count)  }
    clickCounts.print()

    ssc.start()
    ssc.awaitTermination()
  }


}

object AvroUtil {
  val reader = new SpecificDatumReader[ClickEvent](ClickEvent.getClassSchema)

  def clickEventDecode(bytes: Array[Byte]): ClickEvent = {
    val decoder = DecoderFactory.get.binaryDecoder(bytes, null)
    reader.read(null, decoder)
  }
}
