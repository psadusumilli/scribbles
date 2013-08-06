package com.vijayrc


class Item(n:String,q:Int) {
  private val name = n
  private val quantity = q

  def print():Unit = {
    println("item:"+name+"|"+quantity+" nos")
  }

  def isSameAs(i:Item):Boolean = {
    return name.equalsIgnoreCase(i.name)
  }
}
