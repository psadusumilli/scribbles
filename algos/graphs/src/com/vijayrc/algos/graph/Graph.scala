package com.vijayrc.algos.graph

import scala.collection.mutable.ListBuffer
import scala.collection.mutable

class Graph {
  val vertices:ListBuffer[Vertex] = new ListBuffer[Vertex]
  var edgesPerVertex:Map[Vertex,mutable.LinkedList[Edge]]  = Map[Vertex,mutable.LinkedList[Edge]]()

  def addVertex(vertex:Vertex) = vertices += vertex

  def addEdge(vertex:Vertex,edge:Edge,directed:Boolean){
    if(edgesPerVertex(vertex) == null)
      edgesPerVertex += (vertex -> new mutable.LinkedList[Edge]())
    edgesPerVertex(vertex).+:(edge)
    if(directed)
      addEdge(edge.y,new Edge(vertex,edge.weight),directed = false)
  }
}
