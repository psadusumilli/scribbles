package com.vijayrc.constructors

abstract class Animal {
  val tail:Boolean
  var predator:Animal

  def sound:String
  def isScary:Boolean = sound.contains("growl")
  def hasTail:Boolean = tail
  final def canEat(prey:Animal):Boolean = this == prey.predator
}
