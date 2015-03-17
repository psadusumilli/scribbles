package com.vijayrc.ordered

import org.scalatest.FunSuite

class RationalTest extends FunSuite{
  test("should add 2 rational numbers"){
      val r1 = new Rational(1,2)
      val r2 = new Rational(3,5)
      val r3 = r1 + r2;
      assert(r3 == new Rational(11,10))
  }

  test("should throw exception"){
    intercept[IllegalArgumentException]{
      new Rational(4,0)
    }
  }

  test("should compare"){
    val r1 = new Rational(1,2)
    val r2 = new Rational(3,5)
    assert(r2 > r1)
  }


}
