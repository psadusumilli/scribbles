#echo "copying jar..."
#cp ../spark-kafka/target/spark-kafka-1.0-SNAPSHOT-jar-with-dependencies.jar .

echo "STREAMER START"
spark-submit  --class com.vijayrc.spark.DirectKafkaWordCount spark-kafka-1.0-SNAPSHOT-jar-with-dependencies.jar 127.0.0.1:9092, test,
echo "STREAMER END"

