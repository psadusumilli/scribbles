package com.vijayrc.function

import org.scalatest.FunSuite

class AllFunctionsTest extends FunSuite{
  val allFunctions = new AllFunctions

  test("curse someone"){
    val curse: String = allFunctions.local("stupid")
    assert(curse == "Hey stupid fucker")
  }

  test("call function literal"){
    val list: List[Int] = List(1, 2, 3, 4, 5, 6, 7)
    val filtered: List[Int] = allFunctions.objects(list)
    filtered.foreach(x => print(x+","))
    assert(28==allFunctions.closure(list))
  }

  test("should join strings"){
    assert("stan|cartman|kyle|kenny|" == allFunctions.repeatedArg("|","stan","cartman","kyle","kenny"))
    assert(1 == allFunctions.recursion(0))
    assert(1 == allFunctions.recursion(1))
    assert(6 == allFunctions.recursion(3))
    allFunctions.currying()
  }

  test("show closure scopes"){
      allFunctions.closureScopes()
  }




}
