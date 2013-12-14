package com.vijayrc.info.boolean

import scala.io.Source
import org.scalatest.FunSuite
import scala.collection.mutable
import scala.collection.immutable.TreeMap

/**
 * Processes documents then saves map of words->list of Postings(file,occurrences)
 */
class InvertedIndex {
  var treeMap = TreeMap[String,mutable.SortedSet[Posting]]()

  def processDoc(filePath:String, fileId:String){
    val lines: Iterator[String] = Source.fromFile(filePath).getLines()
    lines.foreach(line => {line.split("\\s").foreach(token => processToken(token.replaceAll("[.|,|:|;]",""), fileId))})
  }
  def processToken(token: String, fileId:String){
    if(!treeMap.contains(token)) treeMap += (token -> mutable.SortedSet[Posting]())
    val postings = treeMap(token)
    val postingsByFile = postings.filter(p => p.file.equals(fileId))
    if(postingsByFile.isEmpty) treeMap(token).+=(new Posting(fileId))
    else postingsByFile.foreach(p => p.increment)
  }
  def print(){
    var str = ""
    treeMap.keys.foreach(key => {str+=key+"["; treeMap(key).foreach(posting => str += posting+"|"); str+="]\n"})
    println(str)
  }
}

class Posting(val file:String) extends Ordered[Posting]{
  var occurrences = 1
  def increment{occurrences+=1}
  override def equals(obj: scala.Any): Boolean = file.equals(obj.asInstanceOf[Posting].file)
  override def toString: String = file+","+occurrences
  override def compare(that: Posting): Int = file.compareTo(that.file)
}










class InvertedIndexTest extends FunSuite{
  val index = new InvertedIndex

  test("should make a map"){
    index.processDoc(getClass.getResource("/boolean/file1.txt").getFile,"f1")
    index.processDoc(getClass.getResource("/boolean/file2.txt").getFile,"f2")
    index.print()
  }
}
