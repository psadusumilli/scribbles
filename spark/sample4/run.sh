#!/usr/bin/env bash

# this sample is to read and write avro data from kafka with spark
# click event is the avro class being generated for product click by a user on an ecommerce website

#spark ui
#http://localhost:4040/streaming/
#kafka-start.sh

jar=spark-kafka-1.0-SNAPSHOT-jar-with-dependencies.jar
command=$1

if [ ${command} == "write" ]
then
    echo "writing avro events to kafka.."
    java   -cp ../spark-kafka/target/${jar} com.vijayrc.spark.KafkaAvroReadJob write
else
    echo "SPARK START"
    spark-submit --class com.vijayrc.spark.KafkaAvroReadJob ../spark-kafka/target/${jar}
    echo "SPARK END"
fi
