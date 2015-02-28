package com.vijayrc.constructors

class Cat(val tail: Boolean, animal: Animal) extends Animal {
  var predator: Animal = animal

  def sound: String = "meow"
}
