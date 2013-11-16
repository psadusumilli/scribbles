package com.vijayrc.algos.graph

import scala.collection.mutable.ListBuffer
import scala.collection.mutable
import java.util.UUID


/**
* build linked list of edges (adjacency lists) for every vertex
* if undirected, must add another edge entry to the pair vertex
* a vertex hold a list of edges, which in turn contain the pairing vertex plus weight
 * http://www.codeproject.com/Articles/32212/Introduction-to-Graph-with-Breadth-First-Search-BF
*/
class Edge(val node:Node, val weight:Int) {
  def print(){println("\t"+"|"+node.value+"|"+weight)}
}

class Node(val key:UUID,val value:Any) {
  val edges  = new mutable.MutableList[Edge]
  var seen = false

  def print(){
      println(value)
      edges.map(edge => edge.print())
      println("-------")
  }

  def addEdge(edge:Edge,directed:Boolean){
    if(edge.node == this) return
    if(!edges.contains(edge)) edges.+=(edge)
    if(!directed) edge.node.addEdge(new Edge(this,edge.weight),directed = true)
  }

  def unvisitedEdgeNode:Node = {
    edges.find(edge => !edge.node.seen) match{
      case Some(edge) => edge.node
      case None => null
    }
  }

  override def equals(obj: scala.Any): Boolean = {
    key.equals(obj.asInstanceOf[Node].key)
  }
}

class Graph {
  val nodes = new ListBuffer[Node]
  def addNode(node:Node) = nodes += node
  def print = nodes.map{node => node.print()}
}





