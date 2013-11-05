package com.vijayrc.algos.sort

import org.scalatest.FunSuite

class SortTest extends FunSuite{
  test("sort using selection"){test(new SelectionSort,22)}
  test("sort using bubble"){test(new BubbleSort,22)}
  test("sort using insertion"){test(new InsertionSort,22)}
  test("sort using shell"){test(new ShellSort,10)}
  test("sort using merge"){test(new MergeSort,50)}

  private def test(sort:Sort, sz:Int){
    val items: Array[Value] = sample(sz)
    show("before",items)
    sort.on(items)
    show("after",items)
  }
  private def sample(sz:Int):Array[Value] = {
    val arr =  new Array[Value](sz)
    for(i <- 0 until sz) arr(i) = new Value((Math.random()*100).toInt)
    arr
  }
  private def show(tag:String, items: Array[Value]) {
    println(tag+":"+items.foreach(x => print(x + "|")))
  }

}
