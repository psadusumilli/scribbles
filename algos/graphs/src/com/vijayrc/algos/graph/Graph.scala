package com.vijayrc.algos.graph

import scala.collection.mutable.ListBuffer
import scala.collection.mutable
import java.util.UUID


/**
* build linked list of edges (adjacency lists) for every vertex
* if undirected, must add another edge entry to the pair vertex
*/
class Edge(val y:Vertex, val weight:Int)

class Vertex(val key:UUID,val value:Any) {
  override def toString: String = key+"-"+value
  override def equals(obj: scala.Any): Boolean =  this.key.equals(obj.asInstanceOf[Vertex].key)
}

class Graph {
  val vertices = new ListBuffer[Vertex]
  val edges  = mutable.Map[Vertex,mutable.LinkedList[Edge]]()

  def addVertex(vertex:Vertex) = vertices += vertex

  def addEdge(vertex:Vertex,edge:Edge,directed:Boolean){
    if(edge.y == vertex) return //self edge
    if(edges(vertex) == null) edges += (vertex -> new mutable.LinkedList[Edge]) //new list
    if(!edges(vertex).contains(edge)) edges(vertex).+:(edge) //add only if not existing before
    if(!directed) addEdge(edge.y,new Edge(vertex,edge.weight),directed = true) // if undirected, do it again in reverse
  }

  def print{
    vertices.map{
      vertex => vertex
    }
  }
}





