#!/usr/bin/env bash

#this is to start the random words publisher

echo "PRODUCER START"
java -cp spark-kafka-1.0-SNAPSHOT-jar-with-dependencies.jar com.vijayrc.spark.KafkaWordCountProducer localhost:9092 test 5 10
echo "PRODUCER END"

