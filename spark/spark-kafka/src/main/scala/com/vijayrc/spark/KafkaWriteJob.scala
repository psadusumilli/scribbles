package com.vijayrc.spark

import org.apache.log4j.Logger
import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by vijayrc on 1/26/16.
 */
object KafkaWriteJob {

  def main(arg: Array[String]) {

    val logger = Logger.getLogger(this.getClass)

    if (arg.length < 2) {
      logger.error("=> wrong parameters number")
      System.err.println("Usage: SampleJob <path-to-files>")
      System.exit(1)
    }

    val jobName = "SampleJob"
    val conf = new SparkConf().setAppName(jobName)
    val sc = new SparkContext(conf)

    val pathToFiles = arg(0)

    logger.info("=> jobName =" + jobName)
    logger.info("=> pathToFiles =" + pathToFiles)

    val files = sc.textFile(pathToFiles)

    val rowsWithoutSpaces = files.map(_.replaceAll(", ", "|"))

  }


}
