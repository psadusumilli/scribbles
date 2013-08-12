package com.vijayrc.function

import org.scalatest.FunSuite

class MyFindTest extends FunSuite {

  private val myFind = new MyFind

  test("should find files with given criteria"){
    val path = getClass.getResource("/").getFile()
    println("\nfiles containing sample:")
    myFind.filesContaining(path,"sample").foreach(print)
    println("\nfiles ending with .txt:")
    myFind.filesEndingWith(path,".txt").foreach(print)
  }

}
