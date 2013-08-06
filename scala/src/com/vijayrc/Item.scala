package com.vijayrc


class Item {
  private var name = ""
  private var quantity = 0

  def make(n:String,q:Int) = {
    name = n
    quantity = q
  }

  def print():Unit = {
    println("item:"+name+"|"+quantity+" nos")
  }
}
