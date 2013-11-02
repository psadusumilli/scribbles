package com.vijayrc.algos.stack

class StackArray(val initialSz:Integer) extends Stack{
  var arr = new Array[Any](initialSz)
  var index = -1

  def push(item: Any): Stack = {
    index += 1
    if(index >= arr.length) grow()
    arr(index) = item
    this
  }
  def pop(): Any = {
    if(index < 0) return
    if(index <= (arr.length/4)) shrink()
    val value = arr(index)
    index -= 1
    value
  }
  def show(){
    arr.reverse.foreach(a => if (a != null) print(a.toString+"|"))
    println()
  }

  /**double capacity whenever limits reached*/
  private def grow() {
    val temp = new Array[Any](arr.length*2)
    arr copyToArray temp
    arr = temp
  }
  /**shrink by half, whenever the items occupy quarter capacity*/
  private def shrink(){
    val temp = new Array[Any](arr.length/2)
    arr copyToArray temp
    arr = temp
  }
}
