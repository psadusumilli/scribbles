package com.vijayrc.constructors

class Dog(val tail:Boolean,var predator:Animal) extends Animal{
  def sound: String = "bow-wow"
  override def isScary:Boolean = true
}
