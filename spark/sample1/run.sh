#!/usr/bin/env bash

#this is just to run a basic example of reading text files, removing some chars., placing them in output dir

rm -rf output

echo "SPARK START"
spark-submit --class com.vijayrc.spark.SampleJob ../spark-base/target/spark-base-1.0-SNAPSHOT-jar-with-dependencies.jar /home/vijayrc/Projs/VRC5/scribbles/spark/sample1/input.txt /home/vijayrc/Projs/VRC5/scribbles/spark/sample1/output
echo "SPARK END"

tree output
