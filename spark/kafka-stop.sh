#!/usr/bin/env bash

#ps -ef | grep kafka.Kafka | grep -v grep | awk '{print $2}' | xargs kill
#ps -ef | grep zookeeper | grep -v grep | awk '{print $2}' | xargs kill

cd /home/vijayrc/tools/kafka_2.10-0.9.0.0

./bin/kafka-server-stop.sh  &
echo "stopped kafka server"

sleep 2
./bin/zookeeper-server-stop.sh &
echo "stopped zookeeper"


