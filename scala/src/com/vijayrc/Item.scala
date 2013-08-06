package com.vijayrc


class Item(n:String,q:Int) {
  private val name = n
  private val quantity = q

  def describe():String =   "item:"+name+"|"+quantity+" nos"
  def isSameAs(i:Item):Boolean = name.equalsIgnoreCase(i.name)
  def count():Int = {quantity}
}
