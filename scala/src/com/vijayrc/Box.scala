package com.vijayrc

class Box {
  private var items:Set[Item] = Set()
  def has(item:Item):Boolean =  items.exists(i => i.isSameAs(item))
  def add(item:Item){items += item}
  def describe():String =  {
    var desc = "Box:\n"
    items.foreach(i => desc += i.describe()+"\n")
    desc
  }
}
//a object is a singleton just to pack static methods
object Box{
  def canAdd(item:Item):Boolean = item.count() > 0
}