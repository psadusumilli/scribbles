package com.vijayrc.algos.graph

import scala.collection.mutable.ListBuffer
import scala.collection.mutable
import java.util.UUID


/**
* build linked list of edges (adjacency lists) for every vertex
* if undirected, must add another edge entry to the pair vertex
*/
class Edge(val y:Vertex, val weight:Int) {
  def print(){println("\t"+"|"+y.value+"|"+weight)}
}

class Vertex(val key:UUID,val value:Any) {
  val edges  = new mutable.MutableList[Edge]
  def print(){
      println(value)
      edges.map(edge => edge.print())
      println("-------")
  }
  def addEdge(edge:Edge,directed:Boolean){
    if(edge.y == this) return
    if(!edges.contains(edge)) edges.+=(edge)
    if(!directed) edge.y.addEdge(new Edge(this,edge.weight),directed = true)
  }
  override def equals(obj: scala.Any): Boolean =  key.equals(obj.asInstanceOf[Vertex].key)
}

class Graph {
  val vertices = new ListBuffer[Vertex]
  def addVertex(vertex:Vertex) = vertices += vertex
  def print = vertices.map{vertex => vertex.print()}
}





