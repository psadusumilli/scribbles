package com.vijayrc.innerclass

import scala.collection.mutable.ListBuffer

class Graph(val name:String) {
  var nodes1 = List[Node1]()
  var nodes2 = List[Node2]()

  class Node1{
    val peers = new ListBuffer[Node1]
    def connect(node1:Node1){peers+=node1}
  }
  class Node2{
    val peers = new ListBuffer[Graph#Node2]
    def connect(node2:Graph#Node2){peers+=node2}
  }
  def newNode1:Node1 ={
    val node1 = new Node1
    nodes1  = node1::nodes1
    node1
  }
  def newNode2:Node2 ={
    val node2 = new Node2
    nodes2  = node2::nodes2
    node2
  }
}
object Test extends App{
  val g1 = new Graph("g1")
  val g2 = new Graph("g2")

  val n1_a: g1.Node1 = g1.newNode1
  val n1_b: g1.Node1 = g1.newNode1
  val n1_c: g2.Node1 = g2.newNode1
  n1_a.connect(n1_b) //works
  //n1_b.connect(n1_c) //wont compile as node1 type is bound to graph objects

  val n2_a: Graph#Node2 = g1.newNode2
  val n2_b: Graph#Node2 = g2.newNode2
  n2_a.connect(n2_b)
}