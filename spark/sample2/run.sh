#!/usr/bin/env bash
#this sample is to read from kafka in batch and stream mode

echo "SPARK START"
spark-submit --class com.vijayrc.spark.KafkaReadjob ../spark-kafka/target/spark-kafka-1.0-SNAPSHOT-jar-with-dependencies.jar $1
echo "SPARK END"
