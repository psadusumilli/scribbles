package com.vijayrc

import org.scalatest.FunSuite

class IOTest extends FunSuite {
  private val io = new IO

  test("should convert file content to uppercase"){
    print(io.capitalize(getClass.getResource("/sample1.txt").getFile()))
  }

  test("should list all files recursively"){
    val path = getClass.getResource("/").getFile()
    io.list(path)
    println()
    val files = io.listFilesWith(path, ".txt")
    files.foreach(f => println(f.getAbsolutePath))

  }

}
