echo "copying jar..."
cp ../spark-kafka/target/spark-kafka-1.0-SNAPSHOT-jar-with-dependencies.jar .

echo "SPARK START"
spark-submit --class com.vijayrc.spark.SampleKafkaJob spark-kafka-1.0-SNAPSHOT-jar-with-dependencies.jar $1
echo "SPARK END"
