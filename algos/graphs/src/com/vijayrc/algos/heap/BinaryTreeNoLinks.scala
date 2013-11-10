package com.vijayrc.algos.heap

import com.vijayrc.algos.sort.Value
import org.scalatest.FunSuite

class BinaryTreeNoLinks(sz:Int) {
  private val arr = new Array[Value](sz)

  def add(input:Value){
    if(arr(0) == null) {arr(0) = input; return}
    recurse(input,0)
  }
  def recurse(input:Value,index:Int){
    var i = index
    if(i > arr.length) return
    if(arr(i) == null){
      arr(i) = input
    }else if(input < arr(i)){
      i += 2*i
      recurse(input,i)
    }else{
      i += 2*i+1
      recurse(input,i)
    }
  }
  def show(){
     arr.foreach(x => print(x+","))
  }
}

class BinaryTreeNoLinksTest extends FunSuite{
  test("should add to array at right places"){
    val tree = new BinaryTreeNoLinks(10)
    for(x <- 1 until 10) tree.add(new Value(x))
    tree.show()
  }
}