package com.vijayrc.algos.graph

import java.util.UUID

object Run extends App{

  override def main(args: Array[String]) {
    val graph = new Graph
    val noOfVertices = 10

    def randomNo  = Math.round (Math.random()*noOfVertices).toInt
    def randomVertices = graph.vertices.slice(randomNo,randomNo)

    for(i <- 1 to noOfVertices) graph.addVertex(new Vertex(UUID.randomUUID(),i))

    graph.vertices.map{
      vertex => randomVertices.map{
        randomVertex =>
        graph.addEdge(vertex, new Edge(randomVertex,randomNo),directed = true)
      }
    }
    print(graph)

  }


}
