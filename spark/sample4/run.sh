#!/usr/bin/env bash

#spark ui
#http://localhost:4040/streaming/
#kafka-start.sh

jar=spark-kafka-1.0-SNAPSHOT-jar-with-dependencies.jar

echo "writing avro events to kafka.."
java   -cp ../spark-kafka/target/${jar} com.vijayrc.spark.KafkaAvroReadJob write

echo "SPARK START"
spark-submit --class com.vijayrc.spark.KafkaAvroReadJob ../spark-kafka/target/${jar}
echo "SPARK END"

