package com.vijayrc.algos.sort

case class Value(i: Int) extends Ordered[Value] {
  def compare(that: Value) = this.i - that.i
  override def toString: String = i.toString
}

trait Sort {
  def on(items:Array[Value]):Array[Value]
  def show(items:Array[Value])  = {items.foreach(i => print(i+"|")); println()}
  def swap(i: Int, j: Int, items: Array[Value]){
    val temp = items(i)
    items(i) = items(j)
    items(j) = temp
  }
}
