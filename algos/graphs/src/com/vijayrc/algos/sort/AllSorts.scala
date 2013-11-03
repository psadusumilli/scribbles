package com.vijayrc.algos.sort

class AllSorts {}

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
/**
 * move from left to right (i->sz)
 * pick the first element from right subset (i+1->sz)
 * 'insert' into the sorted place in the left subset by repeated swapping within its elements
 * sorted set grows from left to right
 *
 * O(1) extra space
 * O(n)->O(n2) comparisons| presorted->reverse sorted
 * 0->O(n2) swaps| presorted->reverse sorted
 * adaptive for already sorted sets, better than 'selection' sort
 */
class InsertionSort extends Sort{
  def on(items: Array[Value]): Array[Value] = {
    for(i <- 0 until items.size){
      for(j <- i to 1 by -1){ // starting with i, decreasing by 1 upto `1
        if(items(j) < items(j-1))
          swap(j,j-1,items)
      }
    }
    items
  }
}

