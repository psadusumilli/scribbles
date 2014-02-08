package com.vijayrc.constructors

import org.scalatest.FunSuite

class AnimalTest extends FunSuite{

  test("should call subclass"){
    val tiger:Cat = new Tiger
    val dog: Animal = new Dog(true,tiger)
    val cat:Animal = new Cat(true,dog)

    assert(cat.hasTail)
    assert(!cat.isScary)
    assert(dog canEat cat)
    assert(tiger canEat dog)
    assert(dog isScary)
  }



}
