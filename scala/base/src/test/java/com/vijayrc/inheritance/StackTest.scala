package com.vijayrc.inheritance

import org.scalatest.FunSuite

class StackTest extends FunSuite{

  test("traits are stackable"){
    trait A{
      def a = 1
    }
    trait X extends A{
      override def a = {
        print("X")
        super.a
      }
    }
    trait Y extends A{
      override def a = {
        print("Y")
        super.a
      }
    }
    trait Z extends A{
      override def a = {
        print("Z")
        2
      }
    }
    val xy = new AnyRef with X with Y
    println(xy.a)//YX1
    val yx = new AnyRef with Y with X
    println(yx.a)//XY1
    val z = new AnyRef with Z
    println(z.a)//Z2

  }

}
