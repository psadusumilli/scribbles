package com.vijayrc.algos.sort

import org.scalatest.FunSuite
import com.vijayrc.algos.util.Util

class AllSortsTest extends FunSuite{
  test("sort using selection"){test(new SelectionSort,22)}
  test("sort using bubble"){test(new BubbleSort,22)}
  test("sort using insertion"){test(new InsertionSort,22)}
  test("sort using shell"){test(new ShellSort,10)}
  test("sort using merge"){test(new MergeSort,50)}
  test("sort using quick"){test(new QuickSort,10)}
  test("sort using heap bottoms up"){test(new HeapSort,10)}

  private def test(sort:Sort, sz:Int){
    val items: Array[Value] = Util.randomSet(sz)
    show("before",items)
    sort.on(items)
    show("after",items)
  }

  private def show(tag:String, items: Array[Value]) {
    println(tag+":"+items.foreach(x => print(x + "|")))
  }

}
