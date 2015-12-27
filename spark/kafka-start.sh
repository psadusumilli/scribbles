#!/usr/bin/env bash

cd /home/vijayrc/tools/kafka_2.10-0.9.0.0

./bin/zookeeper-server-start.sh config/zookeeper.properties &
echo "started zookeeper"

sleep 2
./bin/kafka-server-start.sh config/server.properties &
echo "started kafka server"

sleep 3
./bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
./bin/kafka-topics.sh --list --zookeeper localhost:2181
echo "created topic test"

sleep 2
echo "waiting for user to push messages..."
./bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test