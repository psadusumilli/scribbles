package com.vijayrc.operator

class Egg(b:String) {
  private val bird = b

  def isOf(b:String):Boolean = {
    bird.equalsIgnoreCase(b)
  }
}
