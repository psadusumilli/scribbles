#!/usr/bin/env bash
#echo "copying jar..."
#cp ../spark-kafka/target/spark-kafka-1.0-SNAPSHOT-jar-with-dependencies.jar .

echo "STREAMER START"
spark-submit  --class com.vijayrc.spark.KafkaWordCount spark-kafka-1.0-SNAPSHOT-jar-with-dependencies.jar localhost cg1 test 1
echo "STREAMER END"

