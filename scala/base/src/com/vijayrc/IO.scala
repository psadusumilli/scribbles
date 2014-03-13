package com.vijayrc

import scala.io.Source
import java.io.File

class IO {
  def capitalize(file:String):String = {
    val lines: Iterator[String] = Source.fromFile(file).getLines()
    var output = ""
    lines.foreach(line => output += line.toUpperCase+"\n")
    output
  }

  def list(fileName:String){
    val file = new File(fileName)
    println(file.getAbsolutePath)
    if(file.isDirectory){
      file.list().foreach(list)
    }
  }

  def listFilesWith(dir:String, ext:String):Array[File] = {
    val directory: File = new File(dir)
    for {file <- directory.listFiles() if file.isFile; if file.getName.endsWith(ext)} yield file
  }

}
