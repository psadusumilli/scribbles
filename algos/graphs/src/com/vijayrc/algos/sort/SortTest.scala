package com.vijayrc.algos.sort

import org.scalatest.FunSuite

class SortTest extends FunSuite{

  test("sort using selection"){
    val sort:Sort = new SelectionSort
    val items: Array[Value] = sample(22)
    show("before",items)
    sort.on(items)
    show("after",items)
  }
  test("sort using insertion"){
    val sort:Sort = new InsertionSort
    val items: Array[Value] = sample(10)
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
