package com.vijayrc

import scala.io.Source

class IO {
  def capitalize(file:String):String = {
    val lines: Iterator[String] = Source.fromFile(file).getLines()
    var output = ""
    lines.foreach(line => output += line.toUpperCase+"\n")
    output
  }

}
