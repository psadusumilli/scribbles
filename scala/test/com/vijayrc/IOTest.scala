package com.vijayrc

import org.scalatest.FunSuite

class IOTest extends FunSuite {

  test("should convert file content to uppercase"){
    val io = new IO
    print(io.capitalize(getClass.getResource("/sample1.txt").getFile()))
  }

}
