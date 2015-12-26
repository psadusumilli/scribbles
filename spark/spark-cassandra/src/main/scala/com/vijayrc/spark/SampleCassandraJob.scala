package com.vijayrc.spark

import org.apache.log4j.Logger
import org.apache.spark.{SparkConf, SparkContext}


/**
  * Created by vijayrc on 12/25/15.
  */
object SampleCassandraJob {

   def main(arg: Array[String]) {

     val logger = Logger.getLogger(this.getClass)

     if (arg.length < 2) {
       logger.error("=> wrong parameters number")
       System.err.println("Usage: SampleJob <path-to-files> <output-path>")
       System.exit(1)
     }

     val jobName = "SampleJob"
     val conf = new SparkConf().setAppName(jobName)
     val sc = new SparkContext(conf)

     val pathToFiles = arg(0)
     val outputPath = arg(1)

     logger.info("=> jobName =" + jobName)
     logger.info("=> pathToFiles =" + pathToFiles)

     val files = sc.textFile(pathToFiles)

     val rowsWithoutSpaces = files.map(_.replaceAll(", ", "|"))
     rowsWithoutSpaces.saveAsTextFile(outputPath)

   }

 }
