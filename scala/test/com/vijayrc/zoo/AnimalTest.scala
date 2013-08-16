package com.vijayrc.zoo

import org.scalatest.FunSuite

class AnimalTest extends FunSuite{

  test("should call subclass"){
    val dog: Animal = new Dog(true,null)
    val cat:Animal = new Cat(true,dog)
    assert(cat.hasTail)
    assert(!cat.isScary)
    assert(dog canEat cat)
  }



}
