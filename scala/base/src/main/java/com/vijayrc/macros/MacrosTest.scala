package com.vijayrc.macros

import org.scalatest.FunSuite
import com.vijayrc.macros.Macros._

class MacrosTest extends FunSuite{
  test("macro should work when both 'then & 'else are given"){
    myif(3>1, {case then => print("ok") case otherwise => print("oops")}) //ok
    myif(1>3, {case otherwise => print("oops") case then => print("ok")}) //oops
  }
  test("macro should work when either 'then or 'otherwise given"){
    myif(3>1, {case then => print("ok")}) //ok
    myif(3>1, {case otherwise => print("oops")})//oops
  }
}
