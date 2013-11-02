package com.vijayrc.algos.queue

class QueueArray(val initialSz:Integer) extends Queue{
  private val arr:Array[Any] = new Array[Any](initialSz)
  private var headIndex:Integer = 0
  private var tailIndex:Integer = 0

  def enqueue(item: Any): Queue = {
    arr(tailIndex) = item
    tailIndex += 1
    this
  }

  def dequeue(): Any = {
    val item = arr(headIndex)
    headIndex += 1
    item
  }

  def isEmpty(): Boolean = ???

  def show() {
    arr.slice(headIndex,tailIndex).foreach(item => print(item +"|"))
  }

  /**double capacity whenever limits reached*/
  private def grow(){

  }
  /**shrink by half, whenever the items occupy quarter capacity*/
  private def shrink(){

  }
}
