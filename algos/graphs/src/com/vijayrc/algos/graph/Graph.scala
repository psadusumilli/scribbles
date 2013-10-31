package com.vijayrc.algos.graph

import scala.collection.mutable.ListBuffer
import scala.collection.mutable
import scala.collection.immutable.HashMap

class Graph {
  val vertices:ListBuffer[Vertex] = new ListBuffer[Vertex]
  val edgesPerVertex:HashMap[Vertex,mutable.LinkedList[Edge]]  = new HashMap[Vertex,mutable.LinkedList[Edge]]()
}
