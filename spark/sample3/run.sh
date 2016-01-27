#!/usr/bin/env bash
#kafka-start.sh

echo "copying jar..."
#cp ../spark-kafka/target/spark-kafka-1.0-SNAPSHOT-jar-with-dependencies.jar .

echo "SPARK START"
spark-submit --class com.vijayrc.spark.KafkaWriteJob spark-kafka-1.0-SNAPSHOT-jar-with-dependencies.jar
echo "SPARK END"

sleep 2
#echo "waiting for user to push messages..."
# /home/vijayrc/tools/kafka_2.10-0.9.0.0/bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic test
