package com.vijayrc.collect

import org.scalatest.FunSuite
import scala.collection.mutable


class CollectTest extends FunSuite{

  test("basic set methods"){
    val boys = Set("cartman","kyle","kenny","stan")
    println(boys - "cartman")
    println(boys + "butters")
    println(boys ++ List("butters","token"))
    println(boys -- List("butters","token"))
  }

  test("basics map methods"){
    var boys = Map[Int,String]() //an immutable map with a var reference, a copy made for every modification
    boys += (1->"A")
    boys += (2->"B")
    println(boys(1))
    println(boys ++ List(3->"C",4->"D"))

    
  }
}
