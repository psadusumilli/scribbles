package com.vijayrc.algos.graph


class BreadthFirstSearch {
  def on(graph:Graph){
    val queue = new java.util.LinkedList[Node]()
    val root: Node = graph.nodes(0)
    root.seen = true
    print(root.value +",")
    queue.add(root)

    while(!queue.isEmpty){
      val node = queue.remove()
      var child = node.unvisitedEdgeNode
      while(child != null){
        child.seen = true
        print(child.value +",")
        queue.add(child)
        child = node.unvisitedEdgeNode
      }
    }
  }

}
