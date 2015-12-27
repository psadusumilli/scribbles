ps -ef | grep kafka.Kafka | grep -v grep | awk '{print $2}' | xargs kill
ps -ef | grep zookeeper | grep -v grep | awk '{print $2}' | xargs kill
