package com.vijayrc.stage

import org.scalatest.FunSuite

class StageTest extends FunSuite{

  test("act everyone act.."){
    val hero = new Hero
    hero.start()

    hero.!("I'll punch you in the face")
    hero ! "let's run around trees"
  }

}
