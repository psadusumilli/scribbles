package com.vijayrc.algos.sort

/**
 * Selection < Bubble < Insertion < Shell < Merge, Binary, Heap < Quick
 * SBISMBHQ
 * Swaps are costlier than compares
 */

class AllSorts {}

/**
* SELECTION
* ##########
* move from left (i ->+ sz)
* 'select' the smallest from remaining right subset[(i+1) -> sz]
* swap it with the picked left i-th element
* sorted subset grows from left to right
*
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
 * BUBBLESORT
 * ##########
 * move from left to right (i->sz)
 * in RIGHT SUBSET (i <- oz) compare/swap adjacent elements in 'right to left' direction
 * a flag helps to identify if swap ever happened, helps in pre-sorted arrays

 * O(1) extra space
 * O(n2) comparisons
 * 0->O(n2) swaps| presorted->reverse sorted
 * adaptive O(n) time for already sorted sets, better than 'selection' sort
 */
class BubbleSort extends Sort{
  def on(items: Array[Value]): Array[Value] = {
    for(i <- 0 until items.size){
      var swapped = false
      for(j <- items.size to i+1 by -1){ // starting with sz, decreasing by 1 upto 'i+1' (left growing subset)
        if(items(j) < items(j-1)){
            swap(j,j-1,items)
            swapped = true
        }
      }
      if(!swapped) items // return if not a single swap had happened
    }
    items
  }
}
/**
 * INSERTION
 * ##########
 * move from left (i ->+ sz)
 * in LEFT SUBSET (0 <- i) compare/swap adjacent elements in 'right to left' direction
 * called 'insert' because the ith element keeps moving itself in the sorted place in LEFT SUBSET
 *
 * O(1) extra space
 * O(n2) comparisons
 * 0->O(n2) swaps| presorted->reverse sorted
 * adaptive O(n) for already sorted sets, better than 'bubble' sort
 */
class InsertionSort extends Sort{
  def on(items: Array[Value]): Array[Value] = {
    for(i <- 0 until items.size){
      for(j <- i to 1 by -1){ // starting with i, decreasing by 1 upto 1 (left growing subset)
        if(items(j) < items(j-1))
          swap(j,j-1,items)
      }
    }
    items
  }
}
