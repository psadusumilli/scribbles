package com.vijayrc.algos.probs

import com.vijayrc.algos.sort.Value
import org.scalatest.FunSuite
import com.vijayrc.algos.util.Util

class NthLargest() {
  private var answer:Value = null

  def of(nth:Int,items:Array[Value]):Value = {
    recurse(items,0,items.size-1,nth)
    answer
  }
  def recurse(items:Array[Value],low:Int,high:Int,n:Int){
    if(low >= high || answer != null) return
    var l = low
    var r = high
    val partition = low
    while(l < r && l < items.size && r > 0){
      while(items(l)<items(partition)) l+=1
      while(items(r)>items(partition)) r-=1
      swap(l,r,items)
    }
    if(n < l){recurse(items,low,l-1,n)}
    else if(n > l){recurse(items,l+1,high,n)}
    else answer = items(l)
  }

  def swap(i: Int, j: Int, items: Array[Value]){
    val temp = items(i)
    items(i) = items(j)
    items(j) = temp
  }
}

class Test extends FunSuite{
  test("should work"){
    val nthLargest = new NthLargest
    val nth: Int = 3
    val set: Array[Value] = Util.randomSet(10)
    val answer: Value = nthLargest.of(nth, set)
    println(Util.show(set)+", "+answer+" is "+(nth+1)+"th smallest")
  }
}

