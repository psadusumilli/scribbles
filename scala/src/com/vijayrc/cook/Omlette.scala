package com.vijayrc.cook


class Omlette {
  private var egg = new Egg("none")
  private var salt = new Salt
  private var pepper = new Pepper

  def +(egg:Egg):Omlette={
    this.egg = egg;this
  }
  def +(salt:Salt):Omlette={
    this.salt = salt;this
  }
  def +(pepper:Pepper):Omlette={
    this.pepper = pepper;this
  }

  def hasEggOf(bird:String):Boolean = {
    egg.isOf(bird)
  }
}
