package com.vijayrc.cook

class Egg(b:String) {
  private val bird = b

  def isOf(b:String):Boolean = {
    bird.equalsIgnoreCase(b)
  }
}
