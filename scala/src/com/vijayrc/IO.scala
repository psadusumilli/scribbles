package com.vijayrc

import scala.io.Source

class IO {
  def print(file:String):Unit = {
    Source.fromFile(file).getLines().foreach(println)
  }

}
