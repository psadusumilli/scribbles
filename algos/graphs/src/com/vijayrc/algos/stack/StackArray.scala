package com.vijayrc.algos.stack


class StackArray(val initialSz:Integer) extends Stack{
  val arr = new Array[Any](initialSz)
  var index = -1

  def push(item: Any): Stack = {
    index += 1
    arr(index) = item
    this
  }
  def pop(): Any = {
    val value = arr(index)
    index -= 1
    value
  }
  def show(){arr.reverse.foreach(a => if (a != null) print(a.toString+"|"))}
}
