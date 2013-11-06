package com.vijayrc.algos.sort


/**
 * Selection < Bubble < Insertion < Shell < Merge, Binary, Heap < Quick
 * SBISMBHQ
 * cost can be calculated depending on swaps, comparison, memory
 * stability is to maintain the sort order even on multiple key sorts
 * remember there is a big difference in logic when an array is used. we used list in java examples
 */

class AllSorts {}

/*******************************************************************************************
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
* not stable
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
/********************************************************************************************
 * BUBBLESORT
 * ##########
 * move from left to right (i->sz)
 * in RIGHT SUBSET (i <- oz) compare/swap adjacent elements in 'right to left' direction
 * a flag helps to identify if swap ever happened, helps in pre-sorted arrays

 * O(1) extra space
 * O(n2) comparisons
 * 0->O(n2) swaps| presorted->reverse sorted
 * adaptive O(n) time for already sorted sets, better than 'selection' sort
 * stable
 */
class BubbleSort extends Sort{
  def on(items: Array[Value]): Array[Value] = {
    for(i <- 0 until items.size){
      var swapped = false
      for(j <- items.size-1 to i+1 by -1){ // starting with sz, decreasing by 1 upto 'i+1' (left growing subset)
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
/*******************************************************************************************
 * INSERTION
 *
 * move from left (i -> sz)
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

/*******************************************************************************************
 * SHELLSHORT
 * ##########
 * pick a band size h=3j+1 until 3j+1 >= sz
 * move from left (i -> sz), pick elements at band width i+h, i+2h
 * do insertion sort on them
 * decrease the band size and again do insertion sort on picked elements
 * Not stable
 */
class ShellSort extends Sort{
  def on(items: Array[Value]): Array[Value] = {
    val size = items.size
    var h = 1
    while(h < size/3){ h = 3*h + 1}
    while(h > 0){
      for(i <- 0 to size; j= i+h if j < size)
        if(items(j) < items(i)) swap(i,j,items)
      h -= 1 //TODO h = h/3 not working
    }
    items
  }
}

/*******************************************************************************************
 * MERGESORT
 * #########
 * split the items, loop and compare elements in right/left subset and copy smallest to new array (merge)
 * do this recursive sort and merge from bottom i.e smallest subset (sz=2) to the final size
 * needs an extra array space
 *
 * Stable
 * O(n) extra space for arrays (as shown)
 * O(lg(n)) extra space for linked lists
 * O(n*lg(n)) time
 * Not adaptive
 * Does not require random access to data
 */
class MergeSort extends Sort{
  def on(items: Array[Value]): Array[Value] = {
    recurse(items)
    items
  }
  def recurse(items: Array[Value]) {
    val sz: Int = items.size
    if(sz < 2) return
    val mid = sz / 2
    val A = items.slice(0, mid)
    val B = items.slice(mid, sz)

    recurse(A)
    recurse(B)

    var a,b,t = 0
    val temp = new Array[Value](sz)
    while (a < A.size && b < B.size) {
      if (A(a) > B(b)) {temp(t) = B(b);b += 1}
      else {temp(t) = A(a);a += 1}
      t += 1
    }
    if(a < A.size){A.slice(a,A.size) copyToArray(temp,t)}
    if(b < B.size){B.slice(b,B.size) copyToArray(temp,t)}
    temp copyToArray items
  }
}

/***************************************************************************************8
 * QUICKSORT
 * #########
 * pick the left-most as partition element
 * move l from left-right, r from right-left
 * swap elements to right places by comparing with partition
 * after each recurse, the partition element goes into the final sorted place
 *
 * Not stable
 * O(lg(n)) extra space (see discussion)
 * O(n2) time, but typically O(n·lg(n)) time
 * Not adaptive
 */
class QuickSort extends Sort{
  def on(items: Array[Value]): Array[Value] = {
    recurse(items,0,items.size -1)
    items
  }

  def recurse(items: Array[Value],low:Integer, high:Integer){
     if(low >= high) return
     val partition = low
     var l = low; var r = high
     while(l<r && l < items.size && r > 0){
       while(items(l) < items (partition)) l+=1
       while(items(r) > items (partition)) r-=1
       swap(l,r,items)
     }
    recurse(items,low,l-1)
    recurse(items,l+1,high)
  }
}