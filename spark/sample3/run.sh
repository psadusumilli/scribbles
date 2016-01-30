#!/usr/bin/env bash

# this sample is to write a stream of string data from a socket server to kafka using cloudera spark-writer
# socket-server => spark => kafka broker  => kafka consumer

# kafka-start.sh
# for stream example start netcat server ncat -l -p 7777
# then keep typing lines

echo "SPARK START"
spark-submit --class com.vijayrc.spark.KafkaWriteJob ../spark-kafka/target/spark-kafka-1.0-SNAPSHOT-jar-with-dependencies.jar stream
echo "SPARK END"

#open a consumer in another window
#echo "waiting for user to push messages..."
#/home/vijayrc/tools/kafka_2.10-0.9.0.0/bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic test

#spark ui
#http://localhost:4040/streaming/