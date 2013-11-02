package com.vijayrc.algos.queue

class QueueArray(val initialSz:Integer) extends Queue{
  private var arr: Array[Any] = new Array[Any](initialSz)
  private var headIndex:Integer = 0
  private var tailIndex:Integer = 0

  def enqueue(item: Any): Queue = {
    if(bandSz == arr.length) grow()
    arr(tailIndex) = item
    tailIndex += 1
    this
  }

  def dequeue(): Any = {
    val item = arr(headIndex)
    arr(headIndex) = null
    headIndex += 1
    if(bandSz <= arr.length/4) shrink()
    item
  }

  def isEmpty: Boolean = tailIndex < 0

  def show() = arr.slice(headIndex,tailIndex).foreach(item => print(item +"|"))

  /**double capacity whenever limits reached */
  private def grow(){
    val newArr = new Array[Any](2*bandSz)
    var newIndex = 0
    arr.slice(headIndex,tailIndex).foreach(item =>
        if(item != null){
          newArr(newIndex) = item
          newIndex += 1
        }
      )
    arr = newArr
    headIndex = 0
    tailIndex = newIndex
  }
  /**shrink by half, whenever the items occupy quarter capacity
    * note the bandSz is the deciding factor
    * */
  private def shrink(){
    grow()
  }

  private def bandSz = tailIndex - headIndex
}
