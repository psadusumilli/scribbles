package com.vijayrc.traitor

import org.scalatest.FunSuite

class CarTest extends FunSuite{
  test("traitor cars"){
    val guzzler:GasGuzzler = new FordFit
    println(guzzler.consumeGas)

    val ram = new Ram
    println(ram.consumeGas+" & "+ram.run+" & "+ram.stop+" & "+ram.willStop)

    val fordFit:FordFit = new FordFit
    println(fordFit.toString+"|"+fordFit.consumeGas)
  }

}
