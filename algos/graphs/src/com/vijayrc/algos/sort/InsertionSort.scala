package com.vijayrc.algos.sort

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
