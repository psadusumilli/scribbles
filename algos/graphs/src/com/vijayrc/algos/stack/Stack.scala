package com.vijayrc.algos.stack

class Node(val value:AnyRef, val next:Node){}

class Stack {
  var head:Node = null

  def push(item:AnyRef):Stack = {
    if(head == null) head = new Node(item,null)
    else head = new Node(item, head)
    this
  }
  def pop():AnyRef = {
    if(head == null) null
    val value = head.value
    head = head.next
    value
  }
  def show(){
    var temp = head
    while(temp != null){
      print(temp.value.toString)
      temp = temp.next
    }
  }
}

