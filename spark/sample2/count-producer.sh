#echo "copying jar..."
#cp ../spark-kafka/target/spark-kafka-1.0-SNAPSHOT-jar-with-dependencies.jar .

echo "PRODUCER START"
java -cp spark-kafka-1.0-SNAPSHOT-jar-with-dependencies.jar com.vijayrc.spark.KafkaWordCountProducer localhost:9092 test 5 10
echo "PRODUCER END"

