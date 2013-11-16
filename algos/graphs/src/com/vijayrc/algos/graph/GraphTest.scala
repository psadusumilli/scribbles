package com.vijayrc.algos.graph

import org.scalatest.FunSuite
import java.util.UUID

class GraphTest extends FunSuite{

  test("should do depth first search"){
    val graph = sample
    graph.print
    println("\ndepth first:")
    new DepthFirstSearch().on(graph)
  }

  test("should do breadth first search"){
    val graph = sample
    graph.print
    println("\nbreadth first:")
    new BreadthFirstSearch().on(graph)
  }

  def sample:Graph = {
    val graph = new Graph
    val noOfNodes = 10
    def randomId = UUID.randomUUID()
    def randomNo = Math.round(Math.random() * noOfNodes).toInt
    def randomNodes = graph.nodes.slice(randomNo, randomNo)

    for (i <- 1 to noOfNodes)
      graph.addNode(new Node(randomId, i))
    graph.nodes.map {
      node => randomNodes.map {
        randomNode => node.addEdge(new Edge(randomNode, randomNo), directed = true)
      }
    }
    graph
  }
}
