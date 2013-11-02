package com.vijayrc.algos.stack

import org.scalatest.FunSuite

class StackTest extends FunSuite{
  test("should push/pop to link based stack"){
    val stack:Stack = new StackLinks
    stack.push("1").push("3").pop()
    stack.push("4").push("2")
    stack.show()
  }
  test("should push/pop to array based stack"){
    val stack:Stack = new StackArray(2)
    stack.push("1").push("3").pop()
    stack.push("4").push("2").push("5").push("6")
    stack.show()
    (1 to 10).foreach(x=>stack.pop())
    stack.show()
  }

}
