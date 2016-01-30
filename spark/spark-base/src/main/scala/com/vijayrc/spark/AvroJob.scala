package com.vijayrc.spark

import java.io.File

import com.databricks.spark.avro._
import org.apache.commons.io.FileUtils
import org.apache.log4j.Logger
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}


/**
 * Created by vijayrc on 1/30/16.
 */
object AvroJob {
  val logger = Logger.getLogger(this.getClass)
  val outputDir: String = "/tmp/output"

  def main(arg: Array[String]) {
    val conf = new SparkConf().setAppName("click-count").setMaster("local[2]")
    val sqlContext = new SQLContext(new SparkContext(conf))

    readAvroAsDatFrames(sqlContext)
  //writeDataFramesAsAvro(sqlContext)

  }
  def readAvroAsDatFrames(sqlContext: SQLContext): Unit = {
    FileUtils.deleteDirectory(new File(outputDir))
    val df = sqlContext.read.format("com.databricks.spark.avro").load("/home/vijayrc/Projs/VRC5/scribbles/spark/sample5/episodes.avro")
    df.filter("doctor > 5").write.avro(outputDir)
  }

  def writeDataFramesAsAvro(sqlContext: SQLContext): Unit ={
    import sqlContext.implicits._

    FileUtils.deleteDirectory(new File(outputDir))
    val df = Seq((2012, 8, "Batman", 9.8), (2012, 8, "Hero", 8.7),  (2012, 7, "Robot", 5.5),  (2011, 7, "Git", 2.0)).toDF("year", "month", "title", "rating")
    df.write.partitionBy("year", "month").avro(outputDir)
  }


}
