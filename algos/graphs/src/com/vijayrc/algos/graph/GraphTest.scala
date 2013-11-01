package com.vijayrc.algos.graph

import org.scalatest.FunSuite
import java.util.UUID

class GraphTest extends FunSuite{

  test("should build a graph"){
    val graph = new Graph
    val noOfVertices = 10
    def randomId = UUID.randomUUID()
    def randomNo  = Math.round (Math.random()*noOfVertices).toInt
    def randomVertices = graph.vertices.slice(randomNo,randomNo)

    for(i <- 1 to noOfVertices)
      graph.addVertex(new Vertex(randomId,i))
    graph.vertices.map{
      vertex => randomVertices.map{randomVertex => vertex.addEdge(new Edge(randomVertex,randomNo),directed = true)}
    }
    graph.print
  }

}
