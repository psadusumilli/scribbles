package com.vijayrc.cook

import org.scalatest.FunSuite

class OmletteTest extends FunSuite{
  test("should make an omlette"){
    val omlette = new Omlette
    omlette + new Egg("chicken") + new Pepper + new Salt
    assert(omlette.hasEggOf{"chicken"})//see flower brackets instead of circle ones
  }

}
