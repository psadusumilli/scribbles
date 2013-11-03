package com.vijayrc.algos.sort

/**
 * move from left (i ->+ sz)
 * 'select' the smallest from remaining right subset[(i+1) -> sz]
 * swap it with the picked left i-th element
 * sorted subset grows from left to right
 * O(1) extra space
 * O(n2) comparisons
 * O(n) swaps
 * not adaptive for already sorted sets
 */
class SelectionSort extends Sort{

  def on(items: Array[Value]): Array[Value] = {
    for(i <- 0 until items.size){
      var smallestIndex = i
      for(j <- i+1 until items.size){
        if(items(j) < items(smallestIndex))
          smallestIndex = j
      }
      swap(i,smallestIndex,items)
    }
    items
  }

}
