package com.vijayrc.algos.queue

class Node(val item:Any){ var next:Node = null }

class QueueLinks extends Queue{
  var head:Node = null
  var tail:Node = null

  def enqueue(item: Any) = {
    if(head == null){
      head = new Node(item)
      tail = head
    }else{
      val newTail = new Node(item)
      tail.next = newTail
      tail  = newTail
    }
    this
  }
  def dequeue(): Any = {
    val item = head.item
    head = head.next
    item
  }
  def show() {
    var cursor = head
    while(cursor != null){
      print(cursor.item+"|")
      cursor = cursor.next
    }
  }
  def isEmpty(): Boolean = {
    head == null
  }
}
