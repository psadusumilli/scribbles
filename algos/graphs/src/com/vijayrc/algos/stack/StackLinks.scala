package com.vijayrc.algos.stack

class Node(val value:Any, val next:Node){
  def print = value.toString
}
/** just have a head node linking others**/
class StackLinks extends Stack{
  var head:Node = null

  def push(item:Any):StackLinks = {
    if(head == null) head = new Node(item,null)
    else head = new Node(item, head)
    this
  }
  def pop():Any = {
    if(head == null) null
    val value = head.value
    head = head.next
    value
  }
  def show(){
    var temp = head
    while(temp != null){
      print(temp.print+"|")
      temp = temp.next
    }
  }
}

