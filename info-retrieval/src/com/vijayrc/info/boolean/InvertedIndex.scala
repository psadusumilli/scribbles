package com.vijayrc.info.boolean

import scala.io.Source
import org.scalatest.FunSuite
import scala.collection.mutable
import scala.collection.immutable.TreeMap

/**
 *  CLASS CODE
 */
class InvertedIndex {
  var dict = TreeMap[String,mutable.SortedSet[String]]()

  def processDoc(filePath:String, fileId:String){
    val lines: Iterator[String] = Source.fromFile(filePath).getLines()
    lines.foreach(line => processLine(line,fileId))

  }

  def processLine(line: String, fileId:String){
    line.split("\\s").foreach(token => processToken(token, fileId))
  }

  def processToken(token: String, fileId:String){
    val list = mutable.SortedSet[String]()
    if(!dict.contains(token)) {dict += (token -> list)}
    dict(token).+=(fileId)
  }

  def print(){
    var str = ""
    dict.keys.foreach(key => {str+=key+"["; dict(key).foreach(doc => str += doc+"|"); str+="]"})
    println(str)
  }
}


/**
 * TEST CODE
 */
class InvertedIndexTest extends FunSuite{
  val index = new InvertedIndex

  test("should make a map"){
    index.processDoc(getClass.getResource("/boolean/file1.txt").getFile,"f1")
    index.processDoc(getClass.getResource("/boolean/file2.txt").getFile,"f2")
    index.print()
  }
}
