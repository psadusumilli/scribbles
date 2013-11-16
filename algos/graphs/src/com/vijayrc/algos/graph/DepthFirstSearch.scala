package com.vijayrc.algos.graph

import java.util

/**
 * uses a stack, starts from a root node, goes farthest to the deepest level in tree
 */
class DepthFirstSearch {

  def on(graph:Graph){
    val stack = new util.Stack[Node]() //stack of visitedNodes
    val root: Node = graph.nodes(0)
    root.seen = true
    print(root.value +",")
    stack.push(root)

    while(!stack.isEmpty){
      val node = stack.peek()
      val child = node.unvisitedEdgeNode

      if(child != null){
        child.seen = true
        print(child.value +",")
        stack.push(child)
      }else{
        stack.pop() // all child nodes have been visited, so need to be in processing stack anymore
      }
    }
  }
}
