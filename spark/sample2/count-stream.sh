#!/usr/bin/env bash
#this is to read words from kafka with the old stream api

echo "STREAMER START"
spark-submit  --class com.vijayrc.spark.KafkaWordCount spark-kafka-1.0-SNAPSHOT-jar-with-dependencies.jar localhost cg1 test 1
echo "STREAMER END"

