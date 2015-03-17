package com.vijayrc.oop

import org.scalatest.FunSuite

class BoxTest extends FunSuite{
  test("should add items and display"){
    val pencil =  new Item("pencil",2)
    val eraser =  new Item("eraser",1)

    val box = new Box
    box.add(pencil)
    box.add(eraser)
    print(box.describe())
    assert(box.has(pencil))
    assert(Box.canAdd(eraser))

    val newBox = Box() //apply function called for empty brackets
    newBox.add(pencil)

  }

}
