#!/usr/bin/env bash
echo "copying jar..."
cp ../spark-kafka/target/spark-kafka-1.0-SNAPSHOT-jar-with-dependencies.jar .

echo "SPARK START"
spark-submit --class com.vijayrc.spark.KafkaReadjob spark-kafka-1.0-SNAPSHOT-jar-with-dependencies.jar $1
echo "SPARK END"
