package com.vijayrc.spark

import java.io.ByteArrayOutputStream
import java.util

import org.apache.avro.io.EncoderFactory
import org.apache.avro.specific.SpecificDatumWriter
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.log4j.Logger

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
    if (args.nonEmpty && args {0}.equalsIgnoreCase("write")) writeAvro()
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

    for (i <- 0 to 10) {
      val clickBytes = serializeClickEvent(newRandomClickEvent)
      val message = new ProducerRecord[String, Array[Byte]]("test", null, clickBytes)
      producer.send(message)
      logger.info("sent message" + i)
      Thread sleep 1000
    }

    def newRandomClickEvent: ClickEvent = {
      val userId = Random.nextInt(5)
      val productId = Random.nextInt(5)
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
  def readAvro(): Unit = {}
}
