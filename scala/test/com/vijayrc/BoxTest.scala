package com.vijayrc

import org.scalatest.FunSuite

class BoxTest extends FunSuite{
  test("should add items and display"){
    val pencil =  new Item("pencil",2)
    val eraser =  new Item("eraser",1)

    val box = new Box
    box.add(pencil)
    box.add(eraser)
    box.print()

  }



}
